package com.ifrabbit.nk.flow.process.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CallLogVo {
    @JsonProperty("outcome")
    private String outcome;
    @JsonProperty("call_uuid")
    private String callUuid;
    @JsonProperty("vars")
    private VarsVo varsVo;
    @JsonProperty("call_time")
    private String callTime;
    @JsonProperty("caller")
    private String caller;

    @JsonProperty("callee")
    private String callee;

    @JsonProperty("type")
    private String type;

    @JsonProperty("ring_time")
    private String ringTime;

    @JsonProperty("hang_up_by")
    private String hangUpBy;

    @JsonProperty("sub_type")
    private String subType;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("team")
    private String team;

    @JsonProperty("team_id")
    private String teamId;

    @JsonProperty("agent")
    private String agent;

    @JsonProperty("agent_id")
    private String agentId;

    @JsonProperty("REMOTE")
    private String remote;


    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getCallUuid() {
        return callUuid;
    }

    public void setCallUuid(String callUuid) {
        this.callUuid = callUuid;
    }

    public VarsVo getVarsVo() {
        return varsVo;
    }

    public void setVarsVo(VarsVo varsVo) {
        this.varsVo = varsVo;
    }


    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getCallee() {
        return callee;
    }

    public void setCallee(String callee) {
        this.callee = callee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRingTime() {
        return ringTime;
    }

    public void setRingTime(String ringTime) {
        this.ringTime = ringTime;
    }

    public String getHangUpBy() {
        return hangUpBy;
    }

    public void setHangUpBy(String hangUpBy) {
        this.hangUpBy = hangUpBy;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }
}
