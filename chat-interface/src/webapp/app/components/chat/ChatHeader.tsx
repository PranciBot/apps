type ChatHeaderProps = {
  onCreate: () => void;
};

export function ChatHeader({ onCreate }: ChatHeaderProps) {
  return (
    <header className="flex flex-col gap-4 lg:flex-row lg:items-end lg:justify-between">
      <div>
        <p className="font-['IBM_Plex_Mono'] text-[11px] uppercase tracking-[0.3em] text-[#6d5b4a]">
          Wweb Chat
        </p>
        <h1 className="mt-2 text-3xl font-semibold tracking-tight text-[#191510] sm:text-4xl">
          Stay close to every conversation.
        </h1>
      </div>
      <div className="flex items-center gap-3">
        <button
          className="rounded-full bg-gradient-to-br from-[#d97a5f] to-[#f2b066] px-5 py-2.5 font-semibold text-[#191510] shadow-[0_12px_24px_rgba(217,122,95,0.24)] transition hover:-translate-y-0.5"
          onClick={onCreate}
          type="button"
        >
          New conversation
        </button>
      </div>
    </header>
  );
}
