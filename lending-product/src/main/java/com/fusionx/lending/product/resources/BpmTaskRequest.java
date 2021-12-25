package com.fusionx.lending.product.resources;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BpmTaskRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("container_id")
	private String containerId;

	@JsonProperty("task_instance_id")
	private String taskInstanceId;

}
