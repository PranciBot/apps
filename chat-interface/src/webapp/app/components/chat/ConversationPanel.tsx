import { Composer } from "./Composer";
import { MessageList } from "./MessageList";
import type { Conversation } from "./types";

type ConversationPanelProps = {
  conversation: Conversation;
};

export function ConversationPanel({ conversation }: ConversationPanelProps) {
  return (
    <div className="flex h-full flex-col gap-4">
      <div className="flex items-start justify-between gap-4">
        <div>
          <p className="font-['IBM_Plex_Mono'] text-[11px] uppercase tracking-[0.3em] text-[#6d5b4a]">
            Selected conversation
          </p>
          <h2 className="mt-2 text-xl font-semibold text-[#191510]">
            {conversation.title}
          </h2>
        </div>
        <span className="rounded-full bg-[#5c8a77]/20 px-3 py-1 text-xs font-semibold text-[#5c8a77]">
          Live
        </span>
      </div>
      <MessageList messages={conversation.messages} />
      <Composer />
    </div>
  );
}
