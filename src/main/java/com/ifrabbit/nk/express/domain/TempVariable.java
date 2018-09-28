package com.ifrabbit.nk.express.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.Condition;
import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.annotations.Id;
import org.springframework.data.mybatis.domains.LongId;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: WangYan
 * @Date: 2018/5/21 10:41
 * @Description:
 */
@Data
@NoArgsConstructor
@Entity(table = "temp_variable")
public class TempVariable{
        @Id
        @Condition
        @Column(name="id")
        private Long id;

        @Condition
        private String t_key;

        private String t_value;
        @Condition
        private Long  processId;
}
