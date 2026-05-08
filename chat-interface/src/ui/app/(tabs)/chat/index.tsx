import { Text } from "@react-navigation/elements";
import { router } from "expo-router";
import { useEffect, useState } from "react";
import { Alert, StyleSheet, View } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
import useConversations from "../../../hooks/useConversations";

export default function ChatListScreen() {
  const { conversations, loading, fetchAll, createConversation, search } =
    useConversations();
  const [createOpen, setCreateOpen] = useState(false);
  const [newName, setNewName] = useState("");
  const [searchOpen, setSearchOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");

  async function handleCreate() {
    if (!newName.trim()) {
      Alert.alert("Validation", "Enter a name");
      return;
    }
    try {
      const id = await createConversation(newName.trim());
      setCreateOpen(false);
      setNewName("");
      if (id) router.push(`/(tabs)/chat/${id}`);
    } catch (e) {
      console.error(e);
      Alert.alert("Error", "Could not create conversation");
    }
  }

  async function handleSearch() {
    if (!searchTerm.trim()) {
      await fetchAll();
      setSearchOpen(false);
      return;
    }
    try {
      await search(searchTerm.trim());
      setSearchOpen(false);
    } catch (e) {
      console.error(e);
      Alert.alert("Error", "Search failed");
    }
  }

  useEffect(() => {
    // fetchAll();
  }, [fetchAll]);

  const a = fetch("http://192.168.1.196:8080/conversations/all").then();

  return (
    <SafeAreaView style={styles.safeArea} edges={["top", "left", "right"]}>
      {/* <View style={styles.container}>
        <View style={styles.header}>
          <View>
            <Text style={styles.kicker}>Chat</Text>
            <Text style={styles.title}>Conversations.</Text>
          </View>

          <View style={styles.headerButtons}>
            <Pressable
              style={styles.searchButton}
              onPress={() => setSearchOpen(true)}
            >
              <MaterialCommunityIcons
                name="magnify"
                color="#0F766E"
                size={20}
              />
            </Pressable>

            <Pressable
              style={styles.addButton}
              onPress={() => setCreateOpen(true)}
            >
              <MaterialCommunityIcons name="plus" color="#FFFFFF" size={20} />
            </Pressable>
          </View>
        </View>

        <Text style={styles.body}>
          Manage conversations here. Tap one to open the detailed chat view.
        </Text>

        {loading ? (
          <ActivityIndicator size="large" color="#0F766E" />
        ) : (
          <ScrollView
            contentContainerStyle={styles.list}
            showsVerticalScrollIndicator={false}
          >
            {conversations.map((conversation) => (
              <Link
                key={conversation.id}
                href={`/(tabs)/chat/${conversation.id}`}
                asChild
              >
                <Pressable style={styles.card}>
                  <View style={styles.cardTopRow}>
                    <View style={styles.cardTitleRow}>
                      <View style={styles.dot} />
                      <Text style={styles.cardTitle}>{conversation.name}</Text>
                    </View>
                    <Text style={styles.time}>
                      {conversation.updatedAt || ""}
                    </Text>
                  </View>
                  <Text style={styles.preview}>
                    {conversation.preview || ""}
                  </Text>
                  <View style={styles.cardFooter}>
                    <View style={styles.badge}>
                      <Text style={styles.badgeText}>
                        {conversation.badge || ""}
                      </Text>
                    </View>
                    <Pressable style={styles.editButton}>
                      <MaterialCommunityIcons
                        name="pencil-outline"
                        color="#0F766E"
                        size={16}
                      />
                      <Text style={styles.editText}>Edit</Text>
                    </Pressable>
                  </View>
                </Pressable>
              </Link>
            ))}
          </ScrollView>
        )}

        <Modal visible={createOpen} animationType="slide" transparent>
          <View style={styles.modalContainer}>
            <View style={styles.modalCard}>
              <Text style={styles.modalTitle}>Create Conversation</Text>
              <TextInput
                placeholder="Conversation name"
                value={newName}
                onChangeText={setNewName}
                style={styles.modalInput}
              />
              <View style={styles.modalActions}>
                <Pressable
                  onPress={() => setCreateOpen(false)}
                  style={styles.modalCancel}
                >
                  <Text>Cancel</Text>
                </Pressable>
                <Pressable onPress={handleCreate} style={styles.modalCreate}>
                  <Text style={{ color: "#FFF", fontWeight: "700" }}>
                    Create
                  </Text>
                </Pressable>
              </View>
            </View>
          </View>
        </Modal>

        <Modal visible={searchOpen} animationType="fade" transparent>
          <View style={styles.modalContainer}>
            <View style={styles.modalCard}>
              <Text style={styles.modalTitle}>Search Conversations</Text>
              <TextInput
                placeholder="Search by name"
                value={searchTerm}
                onChangeText={setSearchTerm}
                style={styles.modalInput}
              />
              <View style={styles.modalActions}>
                <Pressable
                  onPress={() => {
                    setSearchTerm("");
                    fetchAll();
                    setSearchOpen(false);
                  }}
                  style={styles.modalCancel}
                >
                  <Text>Clear</Text>
                </Pressable>
                <Pressable onPress={handleSearch} style={styles.modalCreate}>
                  <Text style={{ color: "#FFF", fontWeight: "700" }}>
                    Search
                  </Text>
                </Pressable>
              </View>
            </View>
          </View>
        </Modal>
      </View> */}
      <View>
        <Text style={styles.kicker}>{a}</Text>
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: "#F8FAFC",
  },
  container: {
    flex: 1,
    backgroundColor: "#F8FAFC",
    paddingHorizontal: 20,
    paddingTop: 20,
    paddingBottom: 12,
  },
  header: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    marginBottom: 14,
  },
  kicker: {
    color: "#0F766E",
    fontSize: 13,
    fontWeight: "700",
    letterSpacing: 1.2,
    textTransform: "uppercase",
    marginBottom: 8,
  },
  title: {
    color: "#0F172A",
    fontSize: 32,
    fontWeight: "800",
    lineHeight: 38,
  },
  headerButtons: {
    flexDirection: "row",
    alignItems: "center",
    gap: 12,
  },
  searchButton: {
    width: 44,
    height: 44,
    borderRadius: 14,
    backgroundColor: "transparent",
    alignItems: "center",
    justifyContent: "center",
    borderWidth: 1,
    borderColor: "#E6F6F2",
  },
  addButton: {
    width: 44,
    height: 44,
    borderRadius: 14,
    backgroundColor: "#0F766E",
    alignItems: "center",
    justifyContent: "center",
  },
  body: {
    color: "#475569",
    fontSize: 16,
    lineHeight: 23,
    marginBottom: 16,
  },
  list: {
    gap: 12,
    paddingBottom: 12,
  },
  card: {
    backgroundColor: "#FFFFFF",
    borderRadius: 22,
    padding: 16,
    borderWidth: 1,
    borderColor: "#E2E8F0",
  },
  cardTopRow: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    marginBottom: 10,
  },
  cardTitleRow: {
    flexDirection: "row",
    alignItems: "center",
    gap: 10,
    flexShrink: 1,
  },
  dot: {
    width: 10,
    height: 10,
    borderRadius: 999,
    backgroundColor: "#0F766E",
  },
  cardTitle: {
    color: "#0F172A",
    fontSize: 17,
    fontWeight: "700",
  },
  time: {
    color: "#64748B",
    fontSize: 12,
    fontWeight: "600",
  },
  preview: {
    color: "#475569",
    fontSize: 15,
    lineHeight: 22,
    marginBottom: 14,
  },
  cardFooter: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
  },
  badge: {
    alignSelf: "flex-start",
    paddingHorizontal: 10,
    paddingVertical: 6,
    borderRadius: 999,
    backgroundColor: "#CCFBF1",
  },
  badgeText: {
    color: "#0F766E",
    fontSize: 12,
    fontWeight: "700",
  },
  editButton: {
    flexDirection: "row",
    alignItems: "center",
    gap: 6,
  },
  editText: {
    color: "#0F766E",
    fontSize: 14,
    fontWeight: "700",
  },
  modalContainer: {
    flex: 1,
    backgroundColor: "rgba(15,23,42,0.4)",
    alignItems: "center",
    justifyContent: "center",
  },
  modalCard: {
    width: "90%",
    backgroundColor: "#FFFFFF",
    borderRadius: 14,
    padding: 18,
    borderWidth: 1,
    borderColor: "#E2E8F0",
  },
  modalTitle: {
    fontSize: 18,
    fontWeight: "800",
    color: "#0F172A",
    marginBottom: 12,
  },
  modalInput: {
    height: 48,
    borderRadius: 12,
    backgroundColor: "#F8FAFC",
    paddingHorizontal: 12,
    borderWidth: 1,
    borderColor: "#E2E8F0",
    marginBottom: 12,
  },
  modalActions: {
    flexDirection: "row",
    justifyContent: "flex-end",
    gap: 12,
  },
  modalCancel: {
    paddingHorizontal: 12,
    paddingVertical: 10,
  },
  modalCreate: {
    paddingHorizontal: 14,
    paddingVertical: 10,
    backgroundColor: "#0F766E",
    borderRadius: 10,
    alignItems: "center",
    justifyContent: "center",
  },
});
