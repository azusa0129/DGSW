package bxnd.sori.dto.assignments;

import bxnd.sori.entity.Assignment;
import java.util.List;

public record AssignmentsResponse(
    List<Assignment> assignments
) {}
