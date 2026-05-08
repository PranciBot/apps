import { useCallback, useState } from "react";

const DEFAULT_API = "http://192.168.1.196:8080";

export default function useConversations(apiBase: string = DEFAULT_API) {
  const [conversations, setConversations] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchAll = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await fetch(`${apiBase}/conversations/all`);
      if (!res.ok) throw new Error(`HTTP ${res.status}: Failed to fetch`);
      const data = await res.json();
      setConversations(data);
    } catch (err) {
      const message =
        err instanceof Error ? err.message : "Network request failed";
      setError(message);
      console.error("fetchAll error:", message);
      setConversations([]);
    } finally {
      setLoading(false);
    }
  }, [apiBase]);

  const createConversation = useCallback(
    async (name: string) => {
      if (!name?.trim()) throw new Error("Name required");
      setLoading(true);
      setError(null);
      try {
        const res = await fetch(`${apiBase}/conversations`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ name: name.trim() }),
        });
        if (!res.ok) throw new Error(`HTTP ${res.status}: create failed`);
        const location =
          res.headers.get("location") || res.headers.get("Location");
        if (location) {
          const id = location.split("/").pop();
          return id || null;
        }
        // fallback: refresh list
        await fetchAll();
        return null;
      } catch (err) {
        const message =
          err instanceof Error ? err.message : "Failed to create conversation";
        setError(message);
        console.error("createConversation error:", message);
        throw err;
      } finally {
        setLoading(false);
      }
    },
    [apiBase, fetchAll],
  );

  const search = useCallback(
    async (name: string) => {
      setLoading(true);
      setError(null);
      try {
        const res = await fetch(
          `${apiBase}/conversations/search?name=${encodeURIComponent(name)}`,
        );
        if (!res.ok) throw new Error(`HTTP ${res.status}: search failed`);
        const data = await res.json();
        setConversations(data);
        return data;
      } catch (err) {
        const message = err instanceof Error ? err.message : "Search failed";
        setError(message);
        console.error("search error:", message);
        setConversations([]);
        throw err;
      } finally {
        setLoading(false);
      }
    },
    [apiBase],
  );

  return {
    conversations,
    loading,
    error,
    fetchAll,
    createConversation,
    search,
    setConversations,
  };
}
