import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig(({ mode }) => ({
  plugins: [react()],
  base: mode === "production" ? "/Student-Performance-Analysis_FSD-Project/" : "/",
  server: {
    port: 5173
  }
}));
