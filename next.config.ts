import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  transpilePackages: ["@material/web", "lit"],
  headers: async () => [
    {
      source: "/sw.js",
      headers: [
        { key: "Cache-Control", value: "no-cache, no-store, must-revalidate" },
        { key: "Content-Type", value: "application/javascript" },
      ],
    },
  ],
};

export default nextConfig;
