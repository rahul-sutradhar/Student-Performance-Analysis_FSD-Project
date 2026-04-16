import { createContext, useContext, useEffect, useMemo, useState } from "react";
import { authApi, getStoredToken, storeToken } from "./api.js";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => getStoredToken());
  const [user, setUser] = useState(null);
  const [authLoading, setAuthLoading] = useState(Boolean(getStoredToken()));

  useEffect(() => {
    async function loadCurrentUser() {
      const existingToken = getStoredToken();
      if (!existingToken) {
        setAuthLoading(false);
        return;
      }

      try {
        const currentUser = await authApi.me();
        setUser(currentUser);
      } catch (error) {
        storeToken(null);
        setToken(null);
        setUser(null);
      } finally {
        setAuthLoading(false);
      }
    }

    loadCurrentUser();
  }, []);

  async function login(credentials) {
    const authResponse = await authApi.login(credentials);
    storeToken(authResponse.token);
    setToken(authResponse.token);
    setUser({
      username: authResponse.username,
      role: authResponse.role
    });
    return authResponse;
  }

  function logout() {
    storeToken(null);
    setToken(null);
    setUser(null);
  }

  const value = useMemo(
    () => ({
      token,
      user,
      isAuthenticated: Boolean(token && user),
      authLoading,
      login,
      logout
    }),
    [token, user, authLoading]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }

  return context;
}
