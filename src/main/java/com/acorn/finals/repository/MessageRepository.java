package com.acorn.finals.repository;

import com.acorn.finals.model.entity.MessageEntity;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepository {
    private final SqlSession sqlSession;

    public List<MessageEntity> selectAll() {
        return sqlSession.selectList("message.selectAll");
    }

    public MessageEntity selectOne(MessageEntity messageEntity) {
        return sqlSession.selectOne("message.selectOne", messageEntity);
    }

    public boolean insert(MessageEntity messageEntity) {
        return sqlSession.insert("message.insert", messageEntity) > 0;
    }

    public boolean update(MessageEntity messageEntity) {
        return sqlSession.update("message.update", messageEntity) > 0;
    }

    public boolean delete(MessageEntity messageEntity) {
        return sqlSession.delete("message.delete", messageEntity) > 0;
    }
}
