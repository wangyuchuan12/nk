package com.ifrabbit.nk.usercenter.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.ifrabbit.nk.express.domain.Problem;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.*;

import java.sql.Date;
import java.sql.Timestamp;

import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;

@Data
@NoArgsConstructor
@Entity(table = "UFLO_TASK")
@Getter
public class UfloTask {
    @Id
    @Column(name = "ID_")
    private Long id;

    @Column(name = "DESCRIPTION_")
    private String description;
    @Condition
    @Column(name = "NODE_NAME_")
    private String nodeName;

    @Column(name = "PROCESS_ID_")
    private Long processId;

    @Column(name = "ASSIGNEE_")
    @Conditions({ @Condition, @Condition(type = CONTAINING, properties = "fuzzyAssignee") })
    private String assignee;

    @Column(name = "BUSINESS_ID_")
    @Conditions({ @Condition, @Condition(type = CONTAINING, properties = "fuzzyBusinessId") })
    private String businessId;

    @Column(name = "COUNTERSIGN_COUNT_")
    private Integer countersignCount;

    @Column(name = "CREATE_DATE_")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @Conditions({ @Condition, @Condition(type = CONTAINING, properties = "fuzzyCreateDate") })
    private Timestamp createDate;

    @Column(name = "DATE_UNIT_")
    private String dateUnit;

    @Column(name = "DUE_ACTION_DATE_")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dueActionDate;

    @Column(name = "DUEDATE_")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dueDate;

    @Column(name = "END_DATE_")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    @Column(name = "OPINION_")
    private String opinion;

    @Column(name = "OWNER_")
    @Conditions({ @Condition, @Condition(type = CONTAINING, properties = "fuzzyOwMer") })
    private String owner;

    @Column(name = "PREV_STATE_")
    private String prevState;

    @Column(name = "PREV_TASK_")
    private String prevTask;

    @Column(name = "PRIORITY_")
    private String priority;
    @Condition
    @Column(name = "PROCESS_INSTANCE_ID_")
    private Long processInstanceId;

    @Column(name = "PROGRESS_")
    private Integer progress;

    @Column(name = "ROOT_PROCESS_INSTANCE_ID_")
    private Long rootProcessInstanceId;

    @Condition
    @Column(name = "STATE_")
    private String state;

    @Column(name = "SUBJECT_")
    private String subject;

    @Column(name = "TASK_NAME_")
    private String taskName;

    @Column(name = "TYPE_")
    private String type;

    @Column(name = "URL_")
    private String url;


    @Conditions({
            @Condition(properties = "problem.problemparts_expressnumber", column = "problemparts_expressnumber")})
    @ManyToOne
    @JoinColumn(name = "BUSINESS_ID_", referencedColumnName = "problemparts_id")
    private Problem problem;




}
