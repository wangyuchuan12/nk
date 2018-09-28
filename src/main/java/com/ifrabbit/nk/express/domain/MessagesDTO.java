package com.ifrabbit.nk.express.domain;

import com.ifrabbit.nk.express.utils.TimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Data@NoArgsConstructor
public class MessagesDTO extends Messages {
	private String fuzzyExpressNumber;
	private String fuzzyContent;
	private String fuzzyType;
	private String fuzzyCreateDate;
	public void setFuzzyCreateDate(String fuzzyCreateDate) throws ParseException {
		this.fuzzyCreateDate = TimeUtil.timePare2(fuzzyCreateDate);//日期处理
	}
}
