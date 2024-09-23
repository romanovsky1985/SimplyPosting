package my.SimplyPosting.utils;

public class Routing {
    public static final String USERS = "api/users";
    public static final String USERS_ID = USERS + "/{id}";
    public static final String USERS_CHECK_USERNAME = USERS + "/if_free_username";
    public static final String USERS_GET_PRIVATE = USERS + "/private";
    public static final String USERS_MODIFICATION = USERS + "/modification";

    public static final String POSTS = "api/posts";
    public static final String POSTS_ID = POSTS + "/{id}";
    public static final String POSTS_GET_CONTENT = POSTS + "/content/{id}";

    public static final String COMMENTS = "api/comments";
    public static final String COMMENTS_ID = COMMENTS + "/{id}";

    public static final String BANS = "api/bans";
}
