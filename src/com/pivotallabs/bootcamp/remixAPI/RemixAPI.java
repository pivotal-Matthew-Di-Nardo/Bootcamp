package com.pivotallabs.bootcamp.remixAPI;

public class RemixAPI {
    public enum API {
        
        CATEGORIES("categories"),
        PRODUCTS("products"),
        RECOMMENDATIONS("recommendations"),
        REVIEWS("reviews"),
        STORES("stores");
        
        private String name;
        
        private API (String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
    }
    
    public enum Format {
        JSON("json"),
        XML("xml");
        
        private String name;
        private Format(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
    }

    
}

