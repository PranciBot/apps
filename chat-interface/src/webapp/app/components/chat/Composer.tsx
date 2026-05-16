export function Composer() {
  return (
    <div className="flex flex-col gap-3 rounded-2xl border border-[#79624e2e] bg-white/90 p-3 sm:flex-row sm:items-center sm:rounded-full">
      <input
        type="text"
        placeholder="Type a reply or drop a note"
        aria-label="Message composer"
        className="flex-1 bg-transparent text-sm text-[#191510] outline-none"
      />
      <button
        className="rounded-full bg-[#a7c4b7] px-4 py-2 text-sm font-semibold text-[#191510] transition hover:-translate-y-0.5"
        type="button"
      >
        Send
      </button>
    </div>
  );
}
