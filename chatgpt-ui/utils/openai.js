
export const sendMessage = (data) => {
    return postJson('openai/sendMessage', data);
};
