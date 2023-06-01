
export const getSettings = () => {
    const settings = useSettings();
    return settings;
};
export const saveSettings = () => {
    const settings = useSettings();
    localStorage.setItem(KEY.LOCAL_CHAT_SETTING, JSON.stringify(settings.value));
};
