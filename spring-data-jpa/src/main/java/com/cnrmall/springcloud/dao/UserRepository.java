package com.cnrmall.springcloud.dao;

import com.cnrmall.springcloud.entity.UserDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author David
 * @date 2022/12/27 9:30
 *
 *  T：存储库管理的域类型（通常是实体/模型类名）
 *  ID：存储库管理的实体的 ID 类型（通常是在 Entity/Model 类中创建的 @Id 的包装类
 *
 *  CrudRepository— 针对特定类型的存储库的通用 CRUD（创建、读取、更新和删除）操作的接口
 *  PagingAndSortingRepository— 扩展CrudRepository以提供额外的方法来使用分页和排序抽象检索实体
 *  JpaRepository— to 的扩展PagingAndSortingRepository提供了 JPA 相关的方法，例如刷新持久化上下文和批量删除记录。
 *          由于继承，它包含前两个存储库接口的所有方法
 */
@Repository
public interface UserRepository  extends CrudRepository<UserDto, Long>, QueryByExampleExecutor<UserDto> {

//    save()— 保存给定的实体。将返回持久化的实体，并初始化所有自动生成的默认字段。
//    saveAll()— 保存一个可迭代的实体。它以可迭代的形式返回所有持久化的实体。
//    findById()— 根据主键值检索单个实体。
//    existsById()— 返回具有给定 ID 的实体是否存在。
//    findAll()— 返回数据库中该类型的所有可用实体的可迭代对象。
//    findAllById()— 返回与给定 ID 匹配的所有实体的可迭代对象。
//    count()— 返回可用实体的总数。
//    deleteById()— 删除具有给定 ID 的实体。
//    delete()— 查找并删除与给定实体匹配的实体。
//    deleteAll()— 删除在给定迭代中匹配的所有实体。如果没有提供可迭代对象，它将删除所有实体。

}
