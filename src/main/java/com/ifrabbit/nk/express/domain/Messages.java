package com.ifrabbit.nk.express.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.*;
import java.io.Serializable;
import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("new")
@Entity(table = "tapp_message")
public class Messages implements Serializable {
	@Id
	@Column(name="id")
	@Condition
	private Long id;//id

	@Column(name = "message_content")
	@Conditions({ @Condition, @Condition(type = CONTAINING, properties = "fuzzyContent") })
	private String messageContent;//消息内容.

	@Column(name = "create_date")
	@Conditions({ @Condition, @Condition(type = CONTAINING, properties = "fuzzyCreateDate") })
	private String createDate;//创建时间.

	@Conditions({ @Condition, @Condition(type = CONTAINING, properties = "fuzzyType") })
	private Integer type;//已读0，未读1

	private String user;//用户

	@Conditions({ @Condition, @Condition(type = CONTAINING, properties = "fuzzyExpressNumber") })
	private Long expressNumber;//问题件运单号

	private Integer problemPartsType;//问题件类型

}
