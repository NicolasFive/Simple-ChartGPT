export const useLocalChats = () =>
  useState(KEY.LOCAL_CHAT_DATA, () => {
    let val = localStorage.getItem(KEY.LOCAL_CHAT_DATA);
    if (!val) return [];
    return JSON.parse(val);
  });

export const useCurChat = () =>
  useState(KEY.LOCAL_CUR_CHAT, () => {
    let val = localStorage.getItem(KEY.LOCAL_CUR_CHAT);
    if (!val) return -1;
    return parseInt(val);
  });

export const useUser = () => useState(KEY.USER_INFO, () => null)


export const useSettings = () => useState(KEY.LOCAL_CHAT_SETTING, () => {
    let val = localStorage.getItem(KEY.LOCAL_CHAT_SETTING);
    if (!val) return {
        model:'gpt-3.5-turbo',
        max_tokens:1000,
        temperature:1,
        top_p:1,
        n:1,
        presence_penalty:0,
        frequency_penalty:0
    };
    return JSON.parse(val);
})
