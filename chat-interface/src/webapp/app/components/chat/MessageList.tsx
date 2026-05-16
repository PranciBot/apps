import type { Message } from "./types";

type MessageListProps = {
  messages: Message[];
};

export function MessageList({ messages }: MessageListProps) {
  return (
    <div className="flex flex-1 flex-col gap-3 overflow-y-auto pr-1">
      {messages.map((message) => {
        const rowAlign =
          message.sender === "you" ? "justify-end" : "justify-start";
        const bubbleTone =
          message.sender === "you"
            ? "bg-gradient-to-br from-[#f7d6b9] to-[#f2b066]"
            : "bg-gradient-to-br from-[#fefaf4] to-[#e6f2ed]";

        return (
          <div key={message.id} className={`flex ${rowAlign}`}>
            <div
              className={`max-w-[72%] rounded-2xl p-3 text-[#191510] shadow-[0_12px_22px_rgba(22,17,11,0.12)] ${bubbleTone}`}
            >
              <p className="text-sm leading-relaxed">{message.text}</p>
              <span className="mt-2 block font-['IBM_Plex_Mono'] text-[11px] text-[#6d5b4a]">
                {message.time}
              </span>
            </div>
          </div>
        );
      })}
    </div>
  );
}
