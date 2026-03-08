package com.itheima.backend.model.dto.space.analyze;

import com.itheima.backend.model.dto.space.analyze.SpaceAnalyzeRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpaceCategoryAnalyzeRequest extends SpaceAnalyzeRequest implements Serializable {

    private static final long serialVersionUID = 1L;
}