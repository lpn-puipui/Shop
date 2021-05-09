package cn.edu.guet.pay;

import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.MonitorHeartbeatSynResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.*;
import com.alipay.demo.trade.model.hb.*;
import com.alipay.demo.trade.model.result.AlipayF2FPayResult;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.model.result.AlipayF2FRefundResult;
import com.alipay.demo.trade.service.AlipayMonitorService;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayMonitorServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.alipay.demo.trade.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Created by liuyangkly on 15/8/9.
 * ��main���������ڲ��Ե��渶api
 * sdk��demo����������ⷴ������ϵ��liuyang.kly@alipay.com
 */
public class Main {
    private static Log log = LogFactory.getLog(Main.class);

    // ֧�������渶2.0����
    private static AlipayTradeService   tradeService;

    // ֧�������渶2.0���񣨼����˽��ױ��Ͻӿ��߼���
    private static AlipayTradeService   tradeWithHBService;

    // ֧�������ױ��Ͻӿڷ��񣬹����Խӿ�apiʹ�ã������Ķ�readme.txt
    private static AlipayMonitorService monitorService;

    static {
        /** һ��Ҫ�ڴ���AlipayTradeService֮ǰ����Configs.init()����Ĭ�ϲ���
         *  Configs���ȡclasspath�µ�zfbinfo.properties�ļ�������Ϣ������Ҳ������ļ���ȷ�ϸ��ļ��Ƿ���classpathĿ¼
         */
        Configs.init("zfbinfo.properties");

        /** ʹ��Configs�ṩ��Ĭ�ϲ���
         *  AlipayTradeService����ʹ�õ�������Ϊ��̬��Ա���󣬲���Ҫ����new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        // ֧�������渶2.0���񣨼����˽��ױ��Ͻӿ��߼���
        tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();

        /** �����Ҫ�ڳ����и���Configs�ṩ��Ĭ�ϲ���, ����ʹ��ClientBuilder���setXXX�����޸�Ĭ�ϲ��� ����ʹ�ô����е�Ĭ������ */
        monitorService = new AlipayMonitorServiceImpl.ClientBuilder()
            .setGatewayUrl("http://mcloudmonitor.com/gateway.do").setCharset("GBK")
            .setFormat("json").build();
    }

    // �򵥴�ӡӦ��
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                    response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

    public static void main(String[] args) {
        Main main = new Main();

        // ϵͳ���̲��Խ��ױ��Ͻӿ�api
        //        main.test_monitor_sys();

        // POS���̲��Խ��ױ��Ͻӿ�api
        //        main.test_monitor_pos();

        // ���Խ��ױ��Ͻӿڵ���
        //        main.test_monitor_schedule_logic();

        // ���Ե��渶2.0֧����ʹ��δ���ɽ��ױ��Ͻӿڵĵ��渶2.0����
        //        main.test_trade_pay(tradeService);

        // ���Բ�ѯ���渶2.0����
        //        main.test_trade_query();

        // ���Ե��渶2.0�˻�
        //        main.test_trade_refund();

        // ���Ե��渶2.0����֧����ά��
        main.test_trade_precreate();
    }

    // ϵͳ�̵ĵ�����������д������ϵͳ������Ҫ��д���ֶ�
    public void test_monitor_sys() {
        // ϵͳ��ʹ�õĽ�����Ϣ��ʽ��json�ַ�������
        List<SysTradeInfo> sysTradeInfoList = new ArrayList<SysTradeInfo>();
        sysTradeInfoList.add(SysTradeInfo.newInstance("00000001", 5.2, HbStatus.S));
        sysTradeInfoList.add(SysTradeInfo.newInstance("00000002", 4.4, HbStatus.F));
        sysTradeInfoList.add(SysTradeInfo.newInstance("00000003", 11.3, HbStatus.P));
        sysTradeInfoList.add(SysTradeInfo.newInstance("00000004", 3.2, HbStatus.X));
        sysTradeInfoList.add(SysTradeInfo.newInstance("00000005", 4.1, HbStatus.X));

        // ��д�쳣��Ϣ������еĻ�
        List<ExceptionInfo> exceptionInfoList = new ArrayList<ExceptionInfo>();
        exceptionInfoList.add(ExceptionInfo.HE_SCANER);
        //        exceptionInfoList.add(ExceptionInfo.HE_PRINTER);
        //        exceptionInfoList.add(ExceptionInfo.HE_OTHER);

        // ��д��չ����������еĻ�
        Map<String, Object> extendInfo = new HashMap<String, Object>();
        //        extendInfo.put("SHOP_ID", "BJ_ZZ_001");
        //        extendInfo.put("TERMINAL_ID", "1234");

        String appAuthToken = "Ӧ����Ȩ����";//������ʵֵ��д

        AlipayHeartbeatSynRequestBuilder builder = new AlipayHeartbeatSynRequestBuilder()
            .setAppAuthToken(appAuthToken).setProduct(Product.FP).setType(Type.CR)
            .setEquipmentId("cr1000001").setEquipmentStatus(EquipStatus.NORMAL)
            .setTime(Utils.toDate(new Date())).setStoreId("store10001").setMac("0a:00:27:00:00:00")
            .setNetworkType("LAN").setProviderId("2088911212323549") // ����ϵͳ��pid
            .setSysTradeInfoList(sysTradeInfoList) // ϵͳ��ͬ��trade_info��Ϣ
            //                .setExceptionInfoList(exceptionInfoList)  // ��д�쳣��Ϣ������еĻ�
            .setExtendInfo(extendInfo) // ��д��չ��Ϣ������еĻ�
        ;

        MonitorHeartbeatSynResponse response = monitorService.heartbeatSyn(builder);
        dumpResponse(response);
    }

    // POS���̵ĵ�����������д������pos������Ҫ��д���ֶ�
    public void test_monitor_pos() {
        // POS����ʹ�õĽ�����Ϣ��ʽ���ַ�������
        List<PosTradeInfo> posTradeInfoList = new ArrayList<PosTradeInfo>();
        posTradeInfoList.add(PosTradeInfo.newInstance(HbStatus.S, "1324", 7));
        posTradeInfoList.add(PosTradeInfo.newInstance(HbStatus.X, "1326", 15));
        posTradeInfoList.add(PosTradeInfo.newInstance(HbStatus.S, "1401", 8));
        posTradeInfoList.add(PosTradeInfo.newInstance(HbStatus.F, "1405", 3));

        // ��д�쳣��Ϣ������еĻ�
        List<ExceptionInfo> exceptionInfoList = new ArrayList<ExceptionInfo>();
        exceptionInfoList.add(ExceptionInfo.HE_PRINTER);

        // ��д��չ����������еĻ�
        Map<String, Object> extendInfo = new HashMap<String, Object>();
        //        extendInfo.put("SHOP_ID", "BJ_ZZ_001");
        //        extendInfo.put("TERMINAL_ID", "1234");

        AlipayHeartbeatSynRequestBuilder builder = new AlipayHeartbeatSynRequestBuilder()
            .setProduct(Product.FP)
            .setType(Type.SOFT_POS)
            .setEquipmentId("soft100001")
            .setEquipmentStatus(EquipStatus.NORMAL)
            .setTime("2015-09-28 11:14:49")
            .setManufacturerPid("2088000000000009")
            // ��д�����̵�֧����pid
            .setStoreId("store200001").setEquipmentPosition("31.2433190000,121.5090750000")
            .setBbsPosition("2869719733-065|2896507033-091").setNetworkStatus("gggbbbgggnnn")
            .setNetworkType("3G").setBattery("98").setWifiMac("0a:00:27:00:00:00")
            .setWifiName("test_wifi_name").setIp("192.168.1.188")
            .setPosTradeInfoList(posTradeInfoList) // POS����ͬ��trade_info��Ϣ
            //                .setExceptionInfoList(exceptionInfoList) // ��д�쳣��Ϣ������еĻ�
            .setExtendInfo(extendInfo) // ��д��չ��Ϣ������еĻ�
        ;

        MonitorHeartbeatSynResponse response = monitorService.heartbeatSyn(builder);
        dumpResponse(response);
    }

    // ���Ե��渶2.0֧��
    public void test_trade_pay(AlipayTradeService service) {
        // (����) �̻���վ����ϵͳ��Ψһ�����ţ�64���ַ����ڣ�ֻ�ܰ�����ĸ�����֡��»��ߣ�
        // �豣֤�̻�ϵͳ�˲����ظ�������ͨ�����ݿ�sequence���ɣ�
        String outTradeNo = "tradepay" + System.currentTimeMillis()
                            + (long) (Math.random() * 10000000L);

        // (����) �������⣬���������û���֧��Ŀ�ġ��硰xxxƷ��xxx�ŵ����ѡ�
        String subject = "xxxƷ��xxx�ŵ굱�渶����";

        // (����) �����ܽ���λΪԪ�����ܳ���1��Ԫ
        // ���ͬʱ�����ˡ����۽�,�����ɴ��۽�,�������ܽ�����,�����������������:�������ܽ�=�����۽�+�����ɴ��۽�
        String totalAmount = "0.01";

        // (����) �������룬�û�֧����Ǯ���ֻ�app�������������ĸ�������
        String authCode = "�û��Լ���֧����������"; // ����ʾ����286648048691290423
        // (��ѡ��������Ҫ�����Ƿ�ʹ��) �����ɴ��۽���������̼�ƽ̨�����ۿۻ���������������Ʒ������ۣ����Խ�������Ʒ�ܼ���д�����ֶΣ�Ĭ��ȫ����Ʒ�ɴ���
        // �����ֵδ����,�������ˡ������ܽ�,�����ɴ��۽� ���ֵĬ��Ϊ�������ܽ�- �����ɴ��۽�
        //        String discountableAmount = "1.00"; //

        // (��ѡ) �������ɴ��۽���������̼�ƽ̨�����ۿۻ�������ˮ��������ۣ��򽫶�Ӧ�����д�����ֶ�
        // �����ֵδ����,�������ˡ������ܽ�,�����۽�,���ֵĬ��Ϊ�������ܽ�-�����۽�
        String undiscountableAmount = "0.0";

        // ����֧�����˺�ID������֧��һ��ǩԼ�˺���֧�ִ���ͬ���տ��˺ţ�(��sellerId��Ӧ��֧�����˺�)
        // ������ֶ�Ϊ�գ���Ĭ��Ϊ��֧����ǩԼ���̻���PID��Ҳ����appid��Ӧ��PID
        String sellerId = "";

        // �������������ԶԽ��׻���Ʒ����һ����ϸ��������������д"������Ʒ3����20.00Ԫ"
        String body = "������Ʒ3����20.00Ԫ";

        // �̻�����Ա��ţ���Ӵ˲�������Ϊ�̻�����Ա������ͳ��
        String operatorId = "test_operator_id";

        // (����) �̻��ŵ��ţ�ͨ���ŵ�ź��̼Һ�̨�������þ�׼���ŵ���ۿ���Ϣ����ѯ֧��������֧��
        String storeId = "test_store_id";

        // ҵ����չ������Ŀǰ�������֧���������ϵͳ�̱��(ͨ��setSysServiceProviderId����)����������ѯ֧��������֧��
        String providerId = "2088100200300400500";
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId(providerId);

        // ֧����ʱ������ɨ�뽻�׶���Ϊ5����
        String timeoutExpress = "5m";

        // ��Ʒ��ϸ�б�����д������Ʒ��ϸ��Ϣ��
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        // ����һ����Ʒ��Ϣ����������ֱ�Ϊ��Ʒid��ʹ�ù��꣩�����ơ����ۣ���λΪ�֣��������������Ҫ�����Ʒ������GoodsDetail
        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx���", 1000, 1);
        // ������һ����Ʒ���������Ʒ��ϸ�б�
        goodsDetailList.add(goods1);

        // ������������ӵ�һ����Ʒ��Ϣ���û�����Ĳ�ƷΪ��������ˢ��������Ϊ5.00Ԫ������������
        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx��ˢ", 500, 2);
        goodsDetailList.add(goods2);

        String appAuthToken = "Ӧ����Ȩ����";//������ʵֵ��д

        // ��������֧������builder�������������
        AlipayTradePayRequestBuilder builder = new AlipayTradePayRequestBuilder()
            //            .setAppAuthToken(appAuthToken)
            .setOutTradeNo(outTradeNo).setSubject(subject).setAuthCode(authCode)
            .setTotalAmount(totalAmount).setStoreId(storeId)
            .setUndiscountableAmount(undiscountableAmount).setBody(body).setOperatorId(operatorId)
            .setExtendParams(extendParams).setSellerId(sellerId)
            .setGoodsDetailList(goodsDetailList).setTimeoutExpress(timeoutExpress);

        // ����tradePay������ȡ���渶Ӧ��
        AlipayF2FPayResult result = service.tradePay(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("֧����֧���ɹ�: )");
                break;

            case FAILED:
                log.error("֧����֧��ʧ��!!!");
                break;

            case UNKNOWN:
                log.error("ϵͳ�쳣������״̬δ֪!!!");
                break;

            default:
                log.error("��֧�ֵĽ���״̬�����׷����쳣!!!");
                break;
        }
    }

    // ���Ե��渶2.0��ѯ����
    public void test_trade_query() {
        // (����) �̻������ţ�ͨ�����̻������Ų�ѯ���渶�Ľ���״̬
        String outTradeNo = "tradepay14817938139942440181";

        // ������ѯ����builder�������������
        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
            .setOutTradeNo(outTradeNo);

        AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("��ѯ���ظö���֧���ɹ�: )");

                AlipayTradeQueryResponse response = result.getResponse();
                dumpResponse(response);

                log.info(response.getTradeStatus());
                if (Utils.isListNotEmpty(response.getFundBillList())) {
                    for (TradeFundBill bill : response.getFundBillList()) {
                        log.info(bill.getFundChannel() + ":" + bill.getAmount());
                    }
                }
                break;

            case FAILED:
                log.error("��ѯ���ظö���֧��ʧ�ܻ򱻹ر�!!!");
                break;

            case UNKNOWN:
                log.error("ϵͳ�쳣������֧��״̬δ֪!!!");
                break;

            default:
                log.error("��֧�ֵĽ���״̬�����׷����쳣!!!");
                break;
        }
    }

    // ���Ե��渶2.0�˿�
    public void test_trade_refund() {
        // (����) �ⲿ�����ţ���Ҫ�˿�׵��̻��ⲿ������
        String outTradeNo = "tradepay14817938139942440181";

        // (����) �˿���ý�����С�ڵ��ڶ�����֧������λΪԪ
        String refundAmount = "0.01";

        // (��ѡ����Ҫ֧���ظ��˻�ʱ����) �̻��˿�����ţ���֧ͬ�������׺��µĲ�ͬ�˿�����Ŷ�Ӧͬһ�ʽ��׵Ĳ�ͬ�˿����룬
        // ������֧ͬ�������׺��¶����ͬ�̻��˿�����ŵ��˿�ף�֧����ֻ�����һ���˿�
        String outRequestNo = "";

        // (����) �˿�ԭ�򣬿���˵���û��˿�ԭ�򣬷���Ϊ�̼Һ�̨�ṩͳ��
        String refundReason = "�����˿�û������";

        // (����) �̻��ŵ��ţ��˿�����¿���Ϊ�̼Һ�̨�ṩ�˿�Ȩ���ж���ͳ�Ƶ����ã���ѯ֧��������֧��
        String storeId = "test_store_id";

        // �����˿�����builder�������������
        AlipayTradeRefundRequestBuilder builder = new AlipayTradeRefundRequestBuilder()
            .setOutTradeNo(outTradeNo).setRefundAmount(refundAmount).setRefundReason(refundReason)
            .setOutRequestNo(outRequestNo).setStoreId(storeId);

        AlipayF2FRefundResult result = tradeService.tradeRefund(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("֧�����˿�ɹ�: )");
                break;

            case FAILED:
                log.error("֧�����˿�ʧ��!!!");
                break;

            case UNKNOWN:
                log.error("ϵͳ�쳣�������˿�״̬δ֪!!!");
                break;

            default:
                log.error("��֧�ֵĽ���״̬�����׷����쳣!!!");
                break;
        }
    }

    // ���Ե��渶2.0����֧����ά��
    public void test_trade_precreate() {
        // (����) �̻���վ����ϵͳ��Ψһ�����ţ�64���ַ����ڣ�ֻ�ܰ�����ĸ�����֡��»��ߣ�
        // �豣֤�̻�ϵͳ�˲����ظ�������ͨ�����ݿ�sequence���ɣ�
        String outTradeNo = "tradeprecreate" + System.currentTimeMillis()
                            + (long) (Math.random() * 10000000L);

        // (����) �������⣬���������û���֧��Ŀ�ġ��硰xxxƷ��xxx�ŵ굱�渶ɨ�����ѡ�
        String subject = "xxxƷ��xxx�ŵ굱�渶ɨ������";

        // (����) �����ܽ���λΪԪ�����ܳ���1��Ԫ
        // ���ͬʱ�����ˡ����۽�,�����ɴ��۽�,�������ܽ�����,�����������������:�������ܽ�=�����۽�+�����ɴ��۽�
        String totalAmount = "0.01";

        // (��ѡ) �������ɴ��۽���������̼�ƽ̨�����ۿۻ�������ˮ��������ۣ��򽫶�Ӧ�����д�����ֶ�
        // �����ֵδ����,�������ˡ������ܽ�,�����۽�,���ֵĬ��Ϊ�������ܽ�-�����۽�
        String undiscountableAmount = "0";

        // ����֧�����˺�ID������֧��һ��ǩԼ�˺���֧�ִ���ͬ���տ��˺ţ�(��sellerId��Ӧ��֧�����˺�)
        // ������ֶ�Ϊ�գ���Ĭ��Ϊ��֧����ǩԼ���̻���PID��Ҳ����appid��Ӧ��PID
        String sellerId = "";

        // �������������ԶԽ��׻���Ʒ����һ����ϸ��������������д"������Ʒ2����15.00Ԫ"
        String body = "������Ʒ3����20.00Ԫ";

        // �̻�����Ա��ţ���Ӵ˲�������Ϊ�̻�����Ա������ͳ��
        String operatorId = "test_operator_id";

        // (����) �̻��ŵ��ţ�ͨ���ŵ�ź��̼Һ�̨�������þ�׼���ŵ���ۿ���Ϣ����ѯ֧��������֧��
        String storeId = "test_store_id";

        // ҵ����չ������Ŀǰ�������֧���������ϵͳ�̱��(ͨ��setSysServiceProviderId����)����������ѯ֧��������֧��
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088621955627409");

        // ֧����ʱ������Ϊ120����
        String timeoutExpress = "120m";

        // ��Ʒ��ϸ�б�����д������Ʒ��ϸ��Ϣ��
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        // ����һ����Ʒ��Ϣ����������ֱ�Ϊ��Ʒid��ʹ�ù��꣩�����ơ����ۣ���λΪ�֣��������������Ҫ�����Ʒ������GoodsDetail
        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxxС���", 1000, 1);
        // ������һ����Ʒ���������Ʒ��ϸ�б�
        goodsDetailList.add(goods1);

        // ������������ӵ�һ����Ʒ��Ϣ���û�����Ĳ�ƷΪ��������ˢ��������Ϊ5.00Ԫ������������
        GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx��ˢ", 500, 2);
        goodsDetailList.add(goods2);

        // ����ɨ��֧������builder�������������
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
            .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
            .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
            .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
            .setTimeoutExpress(timeoutExpress)
            //                .setNotifyUrl("http://www.test-notify-url.com")//֧��������������֪ͨ�̻���������ָ����ҳ��http·��,������Ҫ����
            .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("֧����Ԥ�µ��ɹ�: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                // ��Ҫ�޸�Ϊ���л����ϵ�·��G:\Lanqiao
                String filePath = String.format("D:\\qr-%s.png",
                    response.getOutTradeNo());
                log.info("filePath:" + filePath);
                //                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
                break;

            case FAILED:
                log.error("֧����Ԥ�µ�ʧ��!!!");
                break;

            case UNKNOWN:
                log.error("ϵͳ�쳣��Ԥ�µ�״̬δ֪!!!");
                break;

            default:
                log.error("��֧�ֵĽ���״̬�����׷����쳣!!!");
                break;
        }
    }
}
