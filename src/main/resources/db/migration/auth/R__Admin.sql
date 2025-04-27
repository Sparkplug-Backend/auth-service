WITH inserted_user_id AS (
    INSERT INTO auth.base_user(username, email, phone_number, password_hash)
        VALUES ('admingod', 'admingod@email.com', null, 'password')
           RETURNING id
),
inserted_admin AS (
    INSERT INTO auth.admin(id)
        VALUES ((SELECT * FROM inserted_user_id))
),
admin_god_id AS (
    SELECT id FROM auth.admin_authority WHERE name = 'ADMIN_GOD'
)
INSERT INTO auth.admin_authority_admin(admin_authority_id, admin_id)
    VALUES ((SELECT * FROM admin_god_id), (SELECT * FROM inserted_user_id))