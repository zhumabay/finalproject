CREATE TABLE roles(
     id SERIAL PRIMARY KEY NOT NULL,
     role TEXT NOT NULL
);

CREATE TABLE users(
    id SERIAL PRIMARY KEY NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    full_name TEXT NOT NULL,
    avatar_url TEXT NOT NULL,
    is_online BOOLEAN DEFAULT FALSE,
    exit_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users_roles(
    user_id INT DEFAULT 0,
    roles_id INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (roles_id) REFERENCES roles (id)
);

CREATE TABLE user_relationships(
    id SERIAL PRIMARY KEY NOT NULL,
    first_user_id INT DEFAULT 0,
    CONSTRAINT fk_relationships_first_user
        FOREIGN KEY (first_user_id)
            REFERENCES users (id),
    second_user_id INT DEFAULT 0,
    CONSTRAINT fk_relationships_second_user
        FOREIGN KEY (second_user_id)
            REFERENCES users (id),
    type TEXT NOT NULL
);

CREATE TABLE friend_requests(
    id SERIAL PRIMARY KEY NOT NULL,
    sender_id INT DEFAULT 0,
    CONSTRAINT fk_requests_sender
        FOREIGN KEY (sender_id)
            REFERENCES users (id),
    receiver_id INT DEFAULT 0,
    CONSTRAINT fk_requests_receiver
        FOREIGN KEY (receiver_id)
            REFERENCES users (id),
    status TEXT NOT NULL
);

CREATE TABLE messages(
    id SERIAL PRIMARY KEY NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sender_id INT DEFAULT 0,
    CONSTRAINT fk_messages_senders
        FOREIGN KEY (sender_id)
            REFERENCES users (id),
    receiver_id INT DEFAULT 0,
    CONSTRAINT fk_messages_receivers
        FOREIGN KEY (receiver_id)
            REFERENCES users (id)
);

CREATE TABLE posts(
      id SERIAL PRIMARY KEY NOT NULL,
      text TEXT NOT NULL,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      user_id INT DEFAULT 0,
      CONSTRAINT fk_posts_users
          FOREIGN KEY (user_id)
              REFERENCES users (id),
      image_url TEXT NOT NULL,
      comments_amount INT DEFAULT 0,
      likes_amount INT DEFAULT 0
);

CREATE TABLE comments(
      id SERIAL PRIMARY KEY NOT NULL,
      text TEXT NOT NULL,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      user_id INT DEFAULT 0,
      CONSTRAINT fk_comments_users
          FOREIGN KEY (user_id)
              REFERENCES users (id),
      post_id INT DEFAULT 0,
      CONSTRAINT fk_comments_posts
          FOREIGN KEY (post_id)
              REFERENCES posts (id)
);

CREATE TABLE likes(
     id SERIAL PRIMARY KEY NOT NULL,
     user_id INT DEFAULT 0,
     CONSTRAINT fk_likes_users
         FOREIGN KEY (user_id)
             REFERENCES users (id),
     post_id INT DEFAULT 0,
     CONSTRAINT fk_likes_posts
         FOREIGN KEY (post_id)
             REFERENCES posts (id)
);
