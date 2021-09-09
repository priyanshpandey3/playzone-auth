package com.axis.springjwt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
	
	private int resource_id;
	private int role_id;

	private boolean can_add;
	private boolean can_edit;
	private boolean can_view;
	private boolean can_delete;

}
