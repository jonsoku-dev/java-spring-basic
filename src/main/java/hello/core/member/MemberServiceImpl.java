package hello.core.member;

import hello.core.AppConfig;

public class MemberServiceImpl implements MemberService {

    /**
     *     추상화를 유지한다. (생성자를 이용하여 주입) -> MemberRepository를 의존하지 않는다.
     *     설계 변경으로 MemberServiceImpl 은 MemoryMemberRepository 를 의존하지 않는다!
     *     단지 MemberRepository 인터페이스만 의존한다.
     *     MemberServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다.
     *     MemberServiceImpl 의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부(AppConfig)에서 결정된다.
     *     MemberServiceImpl 은 이제부터 의존관계에 대한 고민은 외부에 맡기고 실행에만 집중하면 된다.
     */
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //test
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
