#0423
```sql
CREATE TABLE Lusers(
    id NUMBER(6) PRIMARY KEY,
    username VARCHAR2(20),
    password VARCHAR2(100)--MD5加密（符合企业效果）
);
```

```sql
INSERT INTO Lusers VALUES(111111,'lpn','lpn1234');
```

```sql
SELECT COUNT (*) FROM Lusers WHERE username='lpn' AND password='lpn1234';
--返回结果为1则用户名和密码匹配
```

- +LC0OF7WjIleq6AjGUccgA==
- bHBuMTIzNA==