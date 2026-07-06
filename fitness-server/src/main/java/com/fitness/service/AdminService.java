package com.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fitness.dto.*;
import com.fitness.entity.Admin;
import com.fitness.entity.User;

/**
 * 绠＄悊鍛樻湇鍔℃帴鍙? */
public interface AdminService extends IService<Admin> {

    /** 绠＄悊鍛樼櫥褰?*/
    Result<?> login(LoginDTO dto);

    /** 鏁版嵁姒傝 */
    Result<AdminStatsDTO> getStats();

    /** 鍒嗛〉鏌ヨ鐢ㄦ埛 */
    Result<?> pageUsers(PageQueryDTO dto);

    /** 绠＄悊鍛樻柊澧炵敤鎴?*/
    Result<?> addUser(RegisterDTO dto);

    /** 绠＄悊鍛樹慨鏀圭敤鎴?*/
    Result<?> updateUser(Long userId, User user);

    /** 绠＄悊鍛樺垹闄ょ敤鎴凤紙绾ц仈鍒犻櫎鎵撳崱璁板綍锛?*/
    Result<?> deleteUser(Long userId);
}
