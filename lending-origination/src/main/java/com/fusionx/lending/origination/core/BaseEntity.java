/*
 * Copyright (c) 2018. LOLC Technology Services Ltd.
 * Author: Ranjith Kodikara
 * Date: 12/12/18 10:45
 */

package com.fusionx.lending.origination.core;

import java.sql.Timestamp;

import javax.persistence.*;

/**
 * 
 * Inherit this class when you define entities... common_seq is a common
 * sequence and the numbers will be shared with different entities ensuring the
 * uniqueness <br>
 * Have id and version columns
 * 
 * @author ranjithk
 * @since 2018-12-20
 * @version 1.0
 * 
 */
@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_generator")
	@SequenceGenerator(name = "common_generator", sequenceName = "common_seq", allocationSize = 1)
	@Column(name = "id", updatable = false, nullable = false)
	protected Long id;
	@Column(name = "sync_ts")
    private Timestamp syncTs;

	public void setId(Long id) {
		this.id = id;
	}

	@Version
	private Long version;

	protected BaseEntity() {
		id = null;
	}

	public Long getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Timestamp getSyncTs() {
		return syncTs;
	}

	public void setSyncTs(Timestamp syncTs) {
		this.syncTs = syncTs;
	}
	
}
