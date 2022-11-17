package com.iteratec.teamdojo.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Progress {

    private Integer achieved;
    private Integer required;
    private Integer totalScore;

    public Double getPercentage() {
        if (this.totalScore == 0) {
            return 0.0;
        }

        return (this.achieved / this.totalScore) * 100.0;
    }

    public boolean isCompleted() {
        return this.achieved >= this.required;
    }
}
