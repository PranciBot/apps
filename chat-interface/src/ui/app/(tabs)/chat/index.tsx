import { MaterialCommunityIcons } from "@expo/vector-icons";
import { Link } from "expo-router";
import { Pressable, ScrollView, StyleSheet, Text, View } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";

const conversations = [
  {
    id: "morning-brief",
    title: "Morning brief",
    preview: "Plan the day with priorities and blocks.",
    updatedAt: "2m ago",
    badge: "Today",
  },
  {
    id: "weekly-review",
    title: "Weekly review",
    preview: "Summarize wins, blockers, and next steps.",
    updatedAt: "Yesterday",
    badge: "Work",
  },
  {
    id: "travel-ideas",
    title: "Travel ideas",
    preview: "Compare destinations and build a shortlist.",
    updatedAt: "Mon",
    badge: "Personal",
  },
];

export default function ChatListScreen() {
  return (
    <SafeAreaView style={styles.safeArea} edges={["top", "left", "right"]}>
      <View style={styles.container}>
        <View style={styles.header}>
          <View>
            <Text style={styles.kicker}>Chat</Text>
            <Text style={styles.title}>Conversations.</Text>
          </View>
          <Pressable style={styles.addButton}>
            <MaterialCommunityIcons name="plus" color="#FFFFFF" size={20} />
          </Pressable>
        </View>

        <Text style={styles.body}>
          Manage conversations here. Tap one to open the detailed chat view.
        </Text>

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
                    <Text style={styles.cardTitle}>{conversation.title}</Text>
                  </View>
                  <Text style={styles.time}>{conversation.updatedAt}</Text>
                </View>
                <Text style={styles.preview}>{conversation.preview}</Text>
                <View style={styles.cardFooter}>
                  <View style={styles.badge}>
                    <Text style={styles.badgeText}>{conversation.badge}</Text>
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
});
