import type { Conversation } from "./types";

type ConversationCardProps = {
  conversation: Conversation;
  isActive: boolean;
  onSelect: (id: string) => void;
};

export function ConversationCard({
  conversation,
  isActive,
  onSelect,
}: ConversationCardProps) {
  const lastMessage =
    conversation.messages[conversation.messages.length - 1] ?? null;

  return (
    <button
      type="button"
      className={`flex w-full flex-col gap-2 rounded-2xl border p-4 text-left transition hover:-translate-y-0.5 ${
        isActive
          ? "border-[#d97a5f]/60 bg-white/80 shadow-[0_18px_32px_rgba(217,122,95,0.18)]"
          : "border-transparent bg-white/70 hover:border-[#5c8a77]/40 hover:shadow-[0_14px_30px_rgba(92,138,119,0.18)]"
      }`}
      aria-pressed={isActive}
      onClick={() => onSelect(conversation.id)}
    >
      <div className="flex items-baseline justify-between gap-3">
        <h3 className="text-base font-semibold text-[#191510]">
          {conversation.title}
        </h3>
        <span className="font-['IBM_Plex_Mono'] text-[11px] text-[#6d5b4a]">
          {conversation.lastUpdated}
        </span>
      </div>
      <p className="text-sm text-[#3b2f26]">{lastMessage?.text}</p>
      <div className="flex items-center justify-between">
        <span className="rounded-full bg-[#f4ede3] px-2.5 py-0.5 font-['IBM_Plex_Mono'] text-[11px] text-[#6d5b4a]">
          {lastMessage?.time}
        </span>
        {conversation.unread > 0 && (
          <span className="rounded-full bg-[#f2b066] px-2.5 py-0.5 text-[11px] font-semibold text-[#191510]">
            {conversation.unread}
          </span>
        )}
      </div>
    </button>
  );
}
