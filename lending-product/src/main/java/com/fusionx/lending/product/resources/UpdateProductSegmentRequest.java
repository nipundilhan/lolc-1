package com.fusionx.lending.product.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UpdateProductSegmentRequest extends AddProductSegmentRequest{
		
		@NotBlank(message="{version.not-null}")
		@Pattern(regexp = "^$|[0-9]+", message = "{version.pattern}") 
		private String version;

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}
}
