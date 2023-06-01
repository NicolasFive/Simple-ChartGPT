import { v4 } from "uuid";

export const addChat = () => {
  const chats = useLocalChats();
  const cur = useCurChat();
  const chat = { id: v4(), messages: [] };
  chats.value = [chat, ...chats.value];
  cur.value = 0;
};

export const getChats = () => {
  const chats = useLocalChats();
  return chats;
};

export const getCur = () => {
  const chats = useLocalChats();
  const cur = useCurChat();
  if (chats.value.length == 0) {
    addChat();
  } else if (cur.value > chats.value.length - 1) {
    cur.value = chats.value.length - 1;
  }
  return cur;
};
export const getCurChat = () => {
  const cur = getCur();
  const chats = getChats();
  const chat = chats.value[cur.value];
  return chat;
};

export const saveChats = () => {
  const chats = useLocalChats();
  const cur = useCurChat();
  localStorage.setItem(KEY.LOCAL_CHAT_DATA, JSON.stringify(chats.value));
  localStorage.setItem(KEY.LOCAL_CUR_CHAT, cur.value);
};

export const delChat = (chat) => {
  const chats = useLocalChats();
  const cur = useCurChat();
  const idx = chats.value.indexOf(chat);
  if (cur.value == chats.value.length - 1) cur.value--;
  chats.value.splice(idx, 1);
  if (chats.value.length==0)
    addChat()
};
export const selectChat = (chat) => {
  const chats = useLocalChats();
  const cur = useCurChat();
  const idx = chats.value.indexOf(chat);
  cur.value = idx;
};

export const clearChats = () => {
  localStorage.removeItem(KEY.LOCAL_CHAT_DATA);
  const chats = useLocalChats();
  const cur = useCurChat();
  chats.value = [];
  cur.value = -1;
};

export const addMessage = (msg) => {
  const chat = getCurChat();
  chat.messages.push(msg);
};

export const delMessage = (msg) => {
  const chat = getCurChat();
  const idx = chat.messages.indexOf(msg);
  chat.messages.splice(idx, 1);
};

export const clearMessage = () => {
  const chat = getCurChat();
  chat.messages = [];
};
