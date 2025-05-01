package com.springboot.letterbackend.friend.service.impl;

import com.springboot.letterbackend.data.entity.Friend;
import com.springboot.letterbackend.common.Status;
import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.data.repository.FriendRepository;
import com.springboot.letterbackend.data.repository.UserRepository;
import com.springboot.letterbackend.friend.dto.response.FriendInfoDTO;
import com.springboot.letterbackend.friend.dto.response.FriendResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendServiceImpl {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    Logger logger = LoggerFactory.getLogger(FriendServiceImpl.class);

    public FriendServiceImpl(UserRepository userRepository, FriendRepository friendRepository) {
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }

    public List<FriendInfoDTO> getFriendList(Long userId) {
        User user =userRepository.getUserById(userId);
        List<Friend> friendList= user.getFriendList();
        List<FriendInfoDTO> friendInfoList =new ArrayList<>();
        for( Friend friend:friendList){
            if(friend.getStatus()==Status.APPROVED){
                User friendUser=friend.getFromUser();
                FriendInfoDTO friendInfoDTO =new FriendInfoDTO(friendUser);
                friendInfoList.add(friendInfoDTO);

            }

        }
        return friendInfoList;

    }
    public void serchUserByParam(String seachParam) {
        //이메일 또는 uid에 해당 글자가 들어가면 전부 출력함.
        List<User> userList=userRepository.getUserByEmailLikeOrUidLike("%"+seachParam+"%","%"+seachParam+"%");
        for(User user:userList){
            logger.info(user.getUid());
            logger.info(user.getEmail());
        }
    }


    //유저 아이디에 해당하는 친구를 찾아 Pending상태를 Accepted 상태로 전환하는 메서드
    public FriendResultDto addFriend(Long taegetId, Long applicatUserId) {
       User friendUser=userRepository.getUserById(taegetId);
       Friend friend=friendRepository.findFriendByFromUserIdAndToUser(applicatUserId, friendUser);
       if(friend.getStatus()==Status.PENDING){
           friend.setStatus(Status.APPROVED);
           friendRepository.save(friend);
       }
        FriendResultDto friendResultDto =new FriendResultDto();
       friendResultDto.setAddSuccessResult(friendResultDto);
        return friendResultDto;
    }

    public FriendResultDto applyFriend(Long userId, Long friendId) {

        User friendUser=userRepository.getUserById(friendId);
        User user=userRepository.getUserById(userId);
        Friend friend=Friend.builder()
                .toUser(friendUser)
                .fromUser(user)
                .status(Status.PENDING)
                .build();
        friendRepository.save(friend);
        FriendResultDto friendResultDto =new FriendResultDto();

        return friendResultDto;
    }






    public List<FriendInfoDTO> getPendingFriendList(Long userId) {
        User user =userRepository.getUserById(userId);
        List<Friend> friendList= user.getFriendList();
        List<FriendInfoDTO> friendInfoList =new ArrayList<>();
        for( Friend friend:friendList){
            if(friend.getStatus()==Status.PENDING){
                User friendUser=friend.getFromUser();
                FriendInfoDTO friendInfoDTO =new FriendInfoDTO(friendUser);
                friendInfoList.add(friendInfoDTO);
            }
        }
        return friendInfoList;
    }

    //유저가 친구 신청했던 기록을 불러옵니다.(그중에 아직 대기중인..)
    public List<FriendInfoDTO> getAppliedFriendList(Long userId) {
        User user =userRepository.getUserById(userId);
        List<Friend> friendList= user.getAppliedUserList();
        List<FriendInfoDTO> friendInfoList =new ArrayList<>();
        for( Friend friend:friendList){
            if(friend.getStatus()==Status.PENDING){
                User friendUser=friend.getToUser();
                FriendInfoDTO friendInfoDTO =new FriendInfoDTO(friendUser);
                friendInfoList.add(friendInfoDTO);

            }

        }
        return friendInfoList;


    }



}
