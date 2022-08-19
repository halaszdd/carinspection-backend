package hu.unideb.inf.carinspection.controller;

import hu.unideb.inf.carinspection.domain.Group;
import lombok.Value;

@Value
public class GroupDTO {

    private long id;
    private String groupName;

    public GroupDTO(Group group) {
        id = group.getId();
        groupName = group.getGroupName();
    }

}
