"use client";

import { formatDateSeparator } from "@/lib/dateUtils";

interface DateSeparatorProps {
  isoDate: string;
}

export function DateSeparator({ isoDate }: DateSeparatorProps) {
  const label = formatDateSeparator(isoDate);
  return (
    <div
      className="flex items-center gap-3 py-4 px-6"
      role="separator"
      aria-label={label}
    >
      {/* Left rule */}
      <div
        className="flex-1 h-px"
        style={{ background: "var(--md-sys-color-outline-variant)" }}
        aria-hidden="true"
      />

      {/* Label chip */}
      <span
        className="shrink-0 select-none"
        style={{
          color: "var(--md-sys-color-on-surface-variant)",
          fontSize: 12,
          fontWeight: 500,
          letterSpacing: "0.4px",
        }}
      >
        {label}
      </span>

      {/* Right rule */}
      <div
        className="flex-1 h-px"
        style={{ background: "var(--md-sys-color-outline-variant)" }}
        aria-hidden="true"
      />
    </div>
  );
}
