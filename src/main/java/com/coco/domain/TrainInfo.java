package com.coco.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import com.coco.domain.TrainInfo.ResponseBody;

@Getter
@Setter
@ToString
public class TrainInfo {

    private TrainResponse response;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TrainResponse {
        private ResponseBody body;

        // Updated method to check if body is present
        public boolean hasTrainItems() {
            return body != null && body.hasTrainItems();
        }

		public void setResponse(ResponseBody responseBody) {
			this.body = responseBody;
			
		}

		public ResponseBody getResponse() {
			return body;
		}
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseBody {
        private Items items;

        // Updated method to check if items is present
        public boolean hasTrainItems() {
            return items != null && items.getItem() != null;
        }

        // Provide getBody method
        public Object getBody() {
            return items;
        }
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Items {
        private List<TrainItem> item;

        // Updated method to check if item is present
        public boolean hasItem() {
            return item != null && !item.isEmpty();
        }
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TrainItem {
        private int trainno;
        private String traingradename;
        private String depplacename;
        private long depplandtime;
        private String arrplacename;
        private long arrplandtime;
        private int adultcharge;
    }
}
