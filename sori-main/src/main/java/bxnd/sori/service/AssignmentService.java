package bxnd.sori.service;

import bxnd.sori.dto.assignment.AssignmentRequest;
import bxnd.sori.dto.assignment.AssignmentResponse;
import bxnd.sori.dto.assignments.AssignmentsResponse;
import bxnd.sori.entity.Assignment;
import bxnd.sori.entity.Member;
import bxnd.sori.exception.errorcode.AuthErrorCode;
import bxnd.sori.repository.AssignmentRepository;
import bxnd.sori.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final MemberRepository memberRepository;

    public AssignmentResponse createAssignment(AssignmentRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        // JWT 인증된 사용자 이름 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // 사용자 찾기 (userNm 기준)
        Member author = memberRepository.findByUserNm(username)
                .orElseThrow(() -> AuthErrorCode.USER_NOT_FOUND.defaultException("작성자 정보가 없습니다."));

        Assignment assignment = Assignment.builder()
                .title(request.title())
                .content(request.content())
                .deadline(LocalDateTime.parse(request.dueDate(), formatter))
                .author(author) //
                .build();

        Assignment saved = assignmentRepository.save(assignment);

        return new AssignmentResponse(
            saved.getId(),
            saved.getTitle(),
            saved.getContent(),
            saved.getDeadline().format(formatter)
        );
    }

    public AssignmentsResponse getAllAssignments() {
        List<Assignment> assignments = assignmentRepository.findAll();
        return new AssignmentsResponse(assignments);
    }
}