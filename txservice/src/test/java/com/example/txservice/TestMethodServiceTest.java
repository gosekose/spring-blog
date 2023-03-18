package com.example.txservice;

import com.example.txservice.domain.Member;
import com.example.txservice.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class TestMethodServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired TestTxService testTxService;
    @Autowired OtherTxService otherTxService;

    @PersistenceContext
    EntityManager em;

    static String email = "kose@naver.com";

    @AfterEach
    public void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("memberTest")
    public void saveTxMember() throws Exception {
        //given
        Member member = Member.builder().userId(UUID.randomUUID().toString())
                .email(email).password(UUID.randomUUID().toString()).build();

        //when
        Member findMember = testTxService.saveAndFindMember(member);

        //then
        assertThat(findMember).isNull();

    }

    @Test
    @DisplayName("memberTest")
    public void saveFlushAndTxMember() throws Exception {
        //given
        Member member = Member.builder().userId(UUID.randomUUID().toString())
                .email(email).password(UUID.randomUUID().toString()).build();

        //when
        Member findMember = testTxService.saveFlushAndFindMember(member);

        //then
        assertThat(findMember.getEmail()).isEqualTo(member.getEmail());

    }

    @Test
    public void saveTx_findOtherTX() throws Exception {
        //given
        Member member = Member.builder().userId(UUID.randomUUID().toString())
                .email(email).password(UUID.randomUUID().toString()).build();

        //when
        Member findMember = testTxService.applySaveTxAndFindOtherTx(member);

        //then
        assertThat(findMember).isNull();
    }

    @Test
    public void saveAndFlushTx_findOtherTX() throws Exception {
        //given
        Member member = Member.builder().userId(UUID.randomUUID().toString())
                .email(email).password(UUID.randomUUID().toString()).build();

        //when
        Member findMember = testTxService.applySaveFlushTxAndFindOtherTx(member);

        //then
        assertThat(findMember).isNull();
    }


    @TestConfiguration
    static class TestTxConfig {

        @Autowired
        private MemberRepository memberRepository;

        @Bean
        TestTxService testTxService() {
            return new TestTxService(memberRepository, otherTxService());
        }

        @Bean
        OtherTxService otherTxService() {
            return new OtherTxService(memberRepository);
        }
    }

    @Slf4j
    @Service
    @RequiredArgsConstructor
    static class TestTxService {

        private final MemberRepository memberRepository;
        private final OtherTxService otherTxService;
        @PersistenceContext
        EntityManager em;

        @Transactional(rollbackFor = Exception.class)
        public Member saveAndFindMember(Member member) {
            log.info("TxActive = {}", TransactionSynchronizationManager.isActualTransactionActive());
            log.info("Isolation Level = {}", TransactionSynchronizationManager.getCurrentTransactionIsolationLevel());
            memberRepository.save(member);
            em.clear();
            Member findMember = memberRepository.findByEmail(member.getEmail());
            return findMember;
        }

        @Transactional
        public Member saveFlushAndFindMember(Member member) {
            log.info("TxActive = {}", TransactionSynchronizationManager.isActualTransactionActive());
            log.info("Isolation Level = {}", TransactionSynchronizationManager.getCurrentTransactionIsolationLevel());
            memberRepository.saveAndFlush(member);
            em.clear();
            Member findMember = memberRepository.findByEmail(member.getEmail());
            return findMember;
        }


        @Transactional
        public Member applySaveTxAndFindOtherTx(Member member) {
            txThreadLog();
            internalSaveTx(member);
            return otherTxService.internalFindOtherTx(member);
        }

        @Transactional
        public Member applySaveFlushTxAndFindOtherTx(Member member) {
            txThreadLog();
            internalSaveAndFlushTx(member);
            return otherTxService.internalFindOtherTx(member);
        }

        @Transactional
        public void internalSaveTx(Member member) {
            txThreadLog();
            memberRepository.save(member);
        }

        @Transactional
        public void internalSaveAndFlushTx(Member member) {
            txThreadLog();
            memberRepository.saveAndFlush(member);
        }

        public void txThreadLog() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("txActive = {}", txActive);
            log.info("현재 Transaction = {}", TransactionSynchronizationManager.getCurrentTransactionName());
        }

    }

    @Slf4j
    @RequiredArgsConstructor
    static class OtherTxService {
        private final MemberRepository memberRepository;

        @Transactional(propagation = Propagation.REQUIRES_NEW)
        public Member internalFindOtherTx(Member member) {
            txThreadLog();
            return memberRepository.findByEmail(member.getEmail());
        }


        public void txThreadLog() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("txActive = {}", txActive);
            log.info("현재 Transaction = {}", TransactionSynchronizationManager.getCurrentTransactionName());
        }
    }
}
