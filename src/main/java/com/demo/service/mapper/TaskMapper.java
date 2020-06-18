package com.demo.service.mapper;

import com.demo.domain.Tasks;
import com.demo.service.dto.TaskDTO;
import javafx.concurrent.Task;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Tasks toTasks(TaskDTO taskDTO);

}
