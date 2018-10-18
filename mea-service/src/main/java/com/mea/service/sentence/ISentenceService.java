package com.mea.service.sentence;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0.0 COPYRIGHT © 2001 - 2018 VOYAGE ONE GROUP INC. ALL RIGHTS RESERVED.
 * @Author jet.xie
 * @Description:
 * @Date: Created at 15:28 2018/10/12.
 */
@Service
public interface ISentenceService {
    /**
     * participle
     *
     * @param sentence sentence
     * @return words
     */
    List<String> participle(String sentence);
}