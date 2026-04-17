const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL?.trim() || "http://localhost:8080/api";
const TOKEN_STORAGE_KEY = "student-performance-token";
const USE_MOCK_MODE_KEY = "use-mock-mode";

// Auto-detect if we should use mock mode
let useMockMode = window.location.hostname === "localhost" ? false : true;

export function getStoredToken() {
  return window.localStorage.getItem(TOKEN_STORAGE_KEY);
}

export function storeToken(token) {
  if (token) {
    window.localStorage.setItem(TOKEN_STORAGE_KEY, token);
    return;
  }

  window.localStorage.removeItem(TOKEN_STORAGE_KEY);
}

export function isUsingMockMode() {
  return useMockMode;
}

// Import mock data at module load
let mockApiService = null;

async function getMockService() {
  if (!mockApiService) {
    try {
      const module = await import("./mockData.js");
      mockApiService = module.mockApiService;
    } catch (err) {
      console.error("Failed to load mock data service", err);
      throw err;
    }
  }
  return mockApiService;
}

async function request(path, options = {}) {
  const token = options.token ?? getStoredToken();
  const headers = {
    "Content-Type": "application/json",
    ...(options.headers || {})
  };

  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }

  // If mock mode is enabled, use mock service directly
  if (useMockMode) {
    const mockService = await getMockService();
    return handleMockRequest(path, options, mockService);
  }

  try {
    const response = await fetch(`${API_BASE_URL}${path}`, {
      headers,
      ...options,
      signal: AbortSignal.timeout(5000) // 5 second timeout
    });

    if (response.status === 204) {
      return null;
    }

    const payload = await response.json().catch(() => null);

    if (!response.ok) {
      const errorMessage = payload?.message || "Something went wrong while contacting the server.";
      const error = new Error(errorMessage);
      error.validationErrors = payload?.validationErrors || {};
      error.status = response.status;
      throw error;
    }

    return payload;
  } catch (error) {
    // If backend is unreachable, switch to mock mode
    if (error.name === "AbortError" || error.message.includes("fetch") || error.message.includes("Failed to fetch")) {
      console.warn("Backend unavailable, switching to demo mode with mock data");
      useMockMode = true;
      window.localStorage.setItem(USE_MOCK_MODE_KEY, "true");
      
      const mockService = await getMockService();
      return handleMockRequest(path, options, mockService);
    }
    throw error;
  }
}

async function handleMockRequest(path, options, mockService) {
  // Handle login
  if (path.includes("/auth/login")) {
    const body = JSON.parse(options.body || "{}");
    return mockService.login(body.username, body.password);
  }
  
  // Handle students endpoints
  if (path.includes("/students")) {
    if (options.method === "POST") {
      const body = JSON.parse(options.body || "{}");
      return mockService.addStudent(body);
    }
    if (options.method === "PUT") {
      const match = path.match(/\/students\/(\d+)/);
      if (match) {
        const body = JSON.parse(options.body || "{}");
        return mockService.updateStudent(match[1], body);
      }
    }
    if (options.method === "DELETE") {
      const match = path.match(/\/students\/(\d+)/);
      if (match) {
        return mockService.deleteStudent(match[1]);
      }
    }
    // GET all or by ID
    const match = path.match(/\/students\/(\d+)/);
    if (match) {
      return mockService.getStudent(match[1]);
    }
    return mockService.getStudents();
  }
  
  // Handle analytics endpoints
  if (path.includes("/analytics")) {
    return mockService.getInsights();
  }
  
  // Handle auth/me
  if (path.includes("/auth/me")) {
    return { username: "admin", role: "ADMIN" };
  }
  
  throw new Error(`Mock endpoint not found: ${path}`);
}

export const studentApi = {
  getAll({ department, search } = {}) {
    const queryParams = new URLSearchParams();
    if (department) {
      queryParams.set("department", department);
    }
    if (search) {
      queryParams.set("search", search);
    }
    const query = queryParams.toString() ? `?${queryParams.toString()}` : "";
    return request(`/students${query}`);
  },
  getById(studentId) {
    return request(`/students/${studentId}`);
  },
  create(student) {
    return request("/students", {
      method: "POST",
      body: JSON.stringify(student)
    });
  },
  update(studentId, student) {
    return request(`/students/${studentId}`, {
      method: "PUT",
      body: JSON.stringify(student)
    });
  },
  remove(studentId) {
    return request(`/students/${studentId}`, {
      method: "DELETE"
    });
  }
};

export const analyticsApi = {
  getSummary() {
    return request("/analytics/summary");
  },
  getTopper() {
    return request("/analytics/topper");
  },
  getAtRisk() {
    return request("/analytics/at-risk");
  },
  getDepartmentAverages() {
    return request("/analytics/department-averages");
  },
  getAttendancePerformance() {
    return request("/analytics/attendance-performance");
  }
};

export const authApi = {
  login(credentials) {
    return request("/auth/login", {
      method: "POST",
      token: null,
      body: JSON.stringify(credentials)
    });
  },
  me() {
    return request("/auth/me");
  }
};
