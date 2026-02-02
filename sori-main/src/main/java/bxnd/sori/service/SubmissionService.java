package bxnd.sori.service;

import bxnd.sori.dto.Submission.SubmissionRequest;
import bxnd.sori.dto.Submission.SubmissionResponse;
import bxnd.sori.entity.Assignment;
import bxnd.sori.entity.Member;
import bxnd.sori.entity.SubmittedAssignment;
import bxnd.sori.repository.AssignmentRepository;
import bxnd.sori.repository.MemberRepository;
import bxnd.sori.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SubmissionService {


    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;
    private final MemberRepository memberRepository;

    public SubmissionResponse createSubmission(SubmissionRequest request) {
        LocalDateTime now = LocalDateTime.now();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUserNm(auth.getName()).orElse(null);
        Assignment assignment = assignmentRepository.findByTitle(request.title())
                .orElseThrow(() -> new IllegalArgumentException("과제를 찾을 수 없습니다."));
        LocalDateTime deadline = assignment.getDeadline();
        boolean isLate = deadline.isBefore(now);
        SubmittedAssignment submittedAssignment = SubmittedAssignment.builder()
                .title(request.title())
                .content(request.content())
                .author(member)
                .date_over_flag(isLate)
                .checked_flag(false)
                .build();
        submissionRepository.save(submittedAssignment);

        return new SubmissionResponse(
                submittedAssignment.getId(),
                request.title(),
                request.content(),
                auth.getName(),
                isLate,
                false
        );
    }

    public List<SubmissionResponse> getSubmissions(String title) {
        List<SubmittedAssignment> submit = submissionRepository.findByTitle(title);

        return submit.stream()
                .map(submission -> new SubmissionResponse(
                        submission.getId(),
                        submission.getTitle(),
                        submission.getContent(),
                        submission.getAuthor().getUserNm(),
                        submission.isDate_over_flag(),
                        submission.isChecked_flag()
                ))
                .toList();
    }

    public SubmissionResponse updateCheckedFlag(String title, Long Id) {
        List<SubmittedAssignment> submit = submissionRepository.findByTitle(title);
        SubmittedAssignment submission = submit.stream()
                .filter(s -> s.getId().equals(Id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("과제를 찾을 수 없습니다."));

        if (submission.isChecked_flag() == false) {
            submission.setChecked_flag(true);
            submissionRepository.save(submission);
        } else {
            submission.setChecked_flag(false);
            submissionRepository.save(submission);
        }
        return new SubmissionResponse(
                submission.getId(),
                submission.getTitle(),
                submission.getContent(),
                submission.getAuthor().getUserNm(),
                submission.isDate_over_flag(),
                submission.isChecked_flag()
        );
    }
}