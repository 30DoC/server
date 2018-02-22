package com.thirty.api.controller;

import com.thirty.api.common.NotFoundException;
import com.thirty.api.domain.ChatRoom;
import com.thirty.api.response.ResultResponse;
import com.thirty.api.response.RoomIdResponse;
import com.thirty.api.response.UserIdResponse;
import com.thirty.api.service.ChatRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ByeongChan on 2018. 2. 10..
 */

@CrossOrigin(origins = "*")
@Api(value = "Chat Room API", description = "Chat Room API", basePath = "/api/v1/chatRoom")
@RestController
@RequestMapping("/api/v1/chatRoom")
public class ChatRoomController {

    @Autowired
    ChatRoomService chatRoomService;

    private static final String NEW_LINE = "\n";

    @ApiOperation(value = "create room", notes = "두 사용자의 ID user1, user2를 받아서 채팅 방이 개설됩니다." +
            NEW_LINE + " user1, user2의 상태가 WAITING인지 확인 후, 방을 개설하고 두 유저의 상태를 CHATTING으로 변경합니다." +
            NEW_LINE + "리턴 값은 네 가지 경우가 있습니다. / SUCCESS : 방이 성공적으로 개설된 경우 / CHOOSING : 상대방이 다른 방을 선택하고 있는 경우" +
            NEW_LINE + "CHATTING : 상대방이 이미 채팅하고 있는경우 / FAIL : ?")
    @RequestMapping(value = "createRoom", method = RequestMethod.POST)
    public ResultResponse createRoom(@RequestParam Long user1Id, @RequestParam Long user2Id) {

        // 상대방에게 push 보내야 함
        String result = chatRoomService.createRoom(user1Id, user2Id);
        ResultResponse resultResponse = ResultResponse.build(result);

        if(resultResponse == null){
            // 404Error OR -1L ?
            throw new NotFoundException();
        }

        return resultResponse;
    }

    @ApiOperation(value = "quit room", notes = "나가려는 사용자 ID와 채팅 방 ID를 받아서 채팅 방을 나갑니다" +
            NEW_LINE + "user의 상태는 WAITING으로 변경되고 나간 채팅방 ID를 리턴합니다.")
    @RequestMapping(value = "quitRoom", method = RequestMethod.POST)
    public RoomIdResponse quitRoom(@RequestParam Long roomId, @RequestParam Long userId) {

        Long quitRoomId = chatRoomService.quitRoom(roomId, userId);
        RoomIdResponse roomIdResponse = RoomIdResponse.build(quitRoomId);

        return roomIdResponse;
    }

    @ApiOperation(value = "choice", notes = "사용자가 채팅을 선택하고 있는 화면. 사용자의 상태를 CHOOSING으로 변경합니다." +
            NEW_LINE + "성공적으로 상태가 바뀌면 user ID를 그대로 리턴합니다.")
    @RequestMapping(value = "choice", method = RequestMethod.POST)
    public UserIdResponse choice(@RequestParam Long userId) {

        // 선택할 때 납치안해가는게 좋을듯
        Long memberId = chatRoomService.choice(userId);
        UserIdResponse userIdResponse = UserIdResponse.build(memberId);

        return userIdResponse;
    }
}
