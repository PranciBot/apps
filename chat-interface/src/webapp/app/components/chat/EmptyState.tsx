type EmptyStateProps = {
  onCreate: () => void;
};

export function EmptyState({ onCreate }: EmptyStateProps) {
  return (
    <div className="flex h-full flex-col items-center justify-center gap-3 text-center">
      <h2 className="text-xl font-semibold text-[#191510]">
        No conversation selected
      </h2>
      <p className="text-sm text-[#3b2f26]">
        Choose a thread on the left or create a new one.
      </p>
      <button
        className="rounded-full bg-gradient-to-br from-[#d97a5f] to-[#f2b066] px-5 py-2.5 font-semibold text-[#191510] shadow-[0_12px_24px_rgba(217,122,95,0.24)] transition hover:-translate-y-0.5"
        onClick={onCreate}
        type="button"
      >
        Create conversation
      </button>
    </div>
  );
}
