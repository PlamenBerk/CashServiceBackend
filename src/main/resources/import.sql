INSERT INTO `cashregister`.`manager` (`name`, `phone`) VALUES ('Pesho', '0889595959');
INSERT INTO `cashregister`.`manager` (`name`, `phone`) VALUES ('Pesho2', '0889595952');
INSERT INTO `cashregister`.`manager` (`name`, `phone`) VALUES ('Pesho3', '0889595953');

INSERT INTO `cashregister`.`client` (`egn`, `tdd`, `address`, `bulstat`, `comment`, `name`, `manager_id`) VALUES ('9526262621', 'Mnt1', 'Mntaddress1', '12121211', 'comment', 'PeshoCl1', '1');
INSERT INTO `cashregister`.`client` (`egn`, `tdd`, `address`, `bulstat`, `comment`, `name`, `manager_id`) VALUES ('9526262622', 'Mnt2', 'Mntaddress2', '12121212', 'comment2', 'PeshoCl2', '2');
INSERT INTO `cashregister`.`client` (`egn`, `tdd`, `address`, `bulstat`, `comment`, `name`, `manager_id`) VALUES ('9526262623', 'Mnt3', 'Mntaddress3', '12121213', 'comment3', 'PeshoCl3', '3');
INSERT INTO `cashregister`.`client` (`egn`, `tdd`, `address`, `bulstat`, `comment`, `name`, `manager_id`) VALUES ('9526262624', 'Mnt4', 'Mntaddress4', '12121214', 'comment4', 'PeshoCl4', '1');
INSERT INTO `cashregister`.`client` (`egn`, `tdd`, `address`, `bulstat`, `comment`, `name`, `manager_id`) VALUES ('9526262625', 'Mnt5', 'Mntaddress5', '12121214', 'comment5', 'PeshoCl5', '2');
INSERT INTO `cashregister`.`client` (`egn`, `tdd`, `address`, `bulstat`, `comment`, `name`, `manager_id`) VALUES ('9526262626', 'Mnt6', 'Mntaddress6', '12121214', 'comment6', 'PeshoCl6', '3');
INSERT INTO `cashregister`.`client` (`egn`, `tdd`, `address`, `bulstat`, `comment`, `name`, `manager_id`) VALUES ('9526262627', 'Mnt7', 'Mntaddress7', '12121214', 'comment7', 'PeshoCl7', '1');
INSERT INTO `cashregister`.`client` (`egn`, `tdd`, `address`, `bulstat`, `comment`, `name`, `manager_id`) VALUES ('9526262628', 'Mnt8', 'Mntaddress8', '12121214', 'comment8', 'PeshoCl8', '2');
INSERT INTO `cashregister`.`client` (`egn`, `tdd`, `address`, `bulstat`, `comment`, `name`, `manager_id`) VALUES ('9526262629', 'Mnt9', 'Mntaddress9', '12121214', 'comment9', 'PeshoCl9', '3');

INSERT INTO `cashregister`.`site` (`id`, `address`, `name`, `phone`, `client_id`) VALUES ('1', 'adrres1', 'name1', 'phone1', '1');
INSERT INTO `cashregister`.`site` (`id`, `address`, `name`, `phone`, `client_id`) VALUES ('2', 'adrres2', 'name2', 'phone2', '2');

INSERT INTO `cashregister`.`device_model` (`id`, `certificate`, `device_num_prefix`, `fiscal_num_prefix`, `manufacturer`, `model`) VALUES ('1', 'certificate', 'MS', 'FN', 'dateks', 'dt23');
INSERT INTO `cashregister`.`device_model` (`id`, `certificate`, `device_num_prefix`, `fiscal_num_prefix`, `manufacturer`, `model`) VALUES ('2', 'certificate2', 'MS2', 'FN2', 'dateks2', 'dt232');
