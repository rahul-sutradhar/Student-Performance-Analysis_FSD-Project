// Mock data for demo/offline mode
export const MOCK_STUDENTS = [
  {
    id: 1,
    name: "Amit Sharma",
    department: "CS",
    math: 78,
    science: 85,
    programming: 90,
    attendance: 92
  },
  {
    id: 2,
    name: "Riya Verma",
    department: "CS",
    math: 88,
    science: 79,
    programming: 85,
    attendance: 90
  },
  {
    id: 3,
    name: "Karan Patil",
    department: "IT",
    math: 65,
    science: 70,
    programming: 72,
    attendance: 80
  },
  {
    id: 4,
    name: "Pooja Nair",
    department: "IT",
    math: 92,
    science: 91,
    programming: 95,
    attendance: 96
  },
  {
    id: 5,
    name: "Rahul Singh",
    department: "CS",
    math: 55,
    science: 60,
    programming: 58,
    attendance: 70
  },
  {
    id: 6,
    name: "Sneha Kulkarni",
    department: "ENTC",
    math: 74,
    science: 81,
    programming: 76,
    attendance: 88
  },
  {
    id: 7,
    name: "Arjun Deshmukh",
    department: "MECHANICAL",
    math: 61,
    science: 59,
    programming: 57,
    attendance: 68
  },
  {
    id: 8,
    name: "Neha Joshi",
    department: "CIVIL",
    math: 83,
    science: 87,
    programming: 80,
    attendance: 91
  }
];

export const MOCK_TOKEN = "demo-token-12345";

export const mockApiService = {
  // Login with mock token
  async login(username, password) {
    await new Promise(resolve => setTimeout(resolve, 500)); // Simulate delay
    if (username === "admin" && password === "admin123") {
      return { token: MOCK_TOKEN };
    }
    throw new Error("Invalid credentials");
  },

  // Get all students
  async getStudents() {
    await new Promise(resolve => setTimeout(resolve, 300));
    return MOCK_STUDENTS;
  },

  // Get student by ID
  async getStudent(id) {
    await new Promise(resolve => setTimeout(resolve, 200));
    const student = MOCK_STUDENTS.find(s => s.id === parseInt(id));
    if (!student) throw new Error("Student not found");
    return student;
  },

  // Add student
  async addStudent(student) {
    await new Promise(resolve => setTimeout(resolve, 300));
    const newStudent = {
      ...student,
      id: Math.max(...MOCK_STUDENTS.map(s => s.id)) + 1
    };
    MOCK_STUDENTS.push(newStudent);
    return newStudent;
  },

  // Update student
  async updateStudent(id, updates) {
    await new Promise(resolve => setTimeout(resolve, 300));
    const index = MOCK_STUDENTS.findIndex(s => s.id === parseInt(id));
    if (index === -1) throw new Error("Student not found");
    MOCK_STUDENTS[index] = { ...MOCK_STUDENTS[index], ...updates };
    return MOCK_STUDENTS[index];
  },

  // Delete student
  async deleteStudent(id) {
    await new Promise(resolve => setTimeout(resolve, 300));
    const index = MOCK_STUDENTS.findIndex(s => s.id === parseInt(id));
    if (index === -1) throw new Error("Student not found");
    MOCK_STUDENTS.splice(index, 1);
    return { success: true };
  },

  // Get insights
  async getInsights() {
    await new Promise(resolve => setTimeout(resolve, 300));
    return {
      totalStudents: MOCK_STUDENTS.length,
      classAverage: 76.5,
      topPerformer: "Pooja Nair",
      supportWatchlist: 2
    };
  }
};
