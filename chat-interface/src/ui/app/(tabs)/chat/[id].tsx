import AssistantMessage from "@/components/AssistantMessage";
import UserMessage from "@/components/UserMessage";
import { MaterialCommunityIcons } from "@expo/vector-icons";
import { router, useLocalSearchParams } from "expo-router";
import {
  Keyboard,
  KeyboardAvoidingView,
  Platform,
  Pressable,
  StyleSheet,
  Text,
  TextInput,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";

export default function ConversationDetailsScreen() {
  const { id } = useLocalSearchParams<{ id: string }>();

  return (
    <SafeAreaView style={styles.safeArea} edges={["top", "left", "right"]}>
      <TouchableWithoutFeedback onPress={Keyboard.dismiss} accessible={false}>
        <KeyboardAvoidingView
          style={styles.container}
          behavior={Platform.OS === "ios" ? "padding" : undefined}
          keyboardVerticalOffset={8}
        >
          <View style={styles.topBar}>
            <Pressable onPress={() => router.back()} style={styles.backButton}>
              <MaterialCommunityIcons
                name="chevron-left"
                color="#0F172A"
                size={26}
              />
            </Pressable>

            <View style={styles.topBarCenter}>
              <Text style={styles.kicker}>Conversation</Text>
              <Text style={styles.title} numberOfLines={1}>
                {id}
              </Text>
            </View>

            <View style={styles.avatar}>
              <MaterialCommunityIcons
                name="account-edit-outline"
                color="#0F766E"
                size={20}
              />
            </View>
          </View>

          <View style={styles.thread}>
            <AssistantMessage
              name={"Assistant"}
              message={
                "This is the detailed conversation view for the selected chat"
              }
            />

            <UserMessage name={"You"} message={"Hello"} />
          </View>

          <View style={styles.composer}>
            <TextInput
              placeholder="Reply in this conversation"
              placeholderTextColor="#94A3B8"
              style={styles.input}
            />
            <View style={styles.sendButton}>
              <MaterialCommunityIcons name="send" color="#FFFFFF" size={18} />
            </View>
          </View>
        </KeyboardAvoidingView>
      </TouchableWithoutFeedback>
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
    paddingBottom: 16,
  },
  topBar: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    marginBottom: 20,
  },
  backButton: {
    width: 44,
    height: 44,
    borderRadius: 14,
    backgroundColor: "#E2E8F0",
    alignItems: "center",
    justifyContent: "center",
  },
  topBarCenter: {
    flex: 1,
    alignItems: "center",
    paddingHorizontal: 12,
  },
  kicker: {
    color: "#0F766E",
    fontSize: 12,
    fontWeight: "700",
    letterSpacing: 1,
    textTransform: "uppercase",
    marginBottom: 4,
  },
  title: {
    color: "#0F172A",
    fontSize: 18,
    fontWeight: "800",
  },
  avatar: {
    width: 44,
    height: 44,
    borderRadius: 14,
    backgroundColor: "#CCFBF1",
    alignItems: "center",
    justifyContent: "center",
  },
  thread: {
    flex: 1,
    gap: 12,
  },
  composer: {
    flexDirection: "row",
    gap: 12,
    alignItems: "center",
    marginTop: 16,
  },
  input: {
    flex: 1,
    height: 54,
    borderRadius: 18,
    backgroundColor: "#FFFFFF",
    paddingHorizontal: 18,
    color: "#0F172A",
    fontSize: 16,
    borderWidth: 1,
    borderColor: "#E2E8F0",
  },
  sendButton: {
    width: 54,
    height: 54,
    borderRadius: 18,
    backgroundColor: "#0F766E",
    alignItems: "center",
    justifyContent: "center",
  },
});
