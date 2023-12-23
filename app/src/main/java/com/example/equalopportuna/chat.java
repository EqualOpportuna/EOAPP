    package com.example.equalopportuna;

    import java.util.ArrayList;
    import java.util.List;

    public class chat extends Friends{

        private String chatDate;
        private String avatar;
        private String chatPreview;

        private static List<chat> chatList = new ArrayList<>();

        public chat(String username, String careerDescription, String avatarName, String connectionDate, String chatDate) {
            super(username, careerDescription, avatarName, connectionDate);
            this.chatDate = chatDate;
        }

        public String getChatDate() {
            return chatDate;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getChatPreview() {
            return chatPreview;
        }

        public static List<chat> getChatList() {
            return chatList;
        }

        public static List<chat> getAllChatList() {
            List<Friends> allFriends = Friends.getFriendList();
            chatList.clear();

            for (int i = 0; i < allFriends.size(); i++) {
                Friends friend = allFriends.get(i);
                // Create a Chat instance using friend information
                chat chat = new chat(friend.getUsername(), friend.getCareerDescription(), friend.getAvatarName(),
                        friend.getConnectionPeriod(), "Some chat date"); // Replace "Some chat date" with the actual chat date
                chatList.add(chat);
            }
            return chatList;
        }
    }