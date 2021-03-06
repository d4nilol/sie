package br.iesb.sie.work;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.hibernate.jdbc.Work;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class FillReportWork implements Work {

    private static final Logger logger = Logger.getLogger(FillReportWork.class.getName());

    private final InputStream reportTemplateAsStream;

    private final Map<String, Object> params;

    private JasperPrint jasperPrint;

    public FillReportWork(InputStream reportTemplateAsStream, Map<String, Object> params) {
        this.reportTemplateAsStream = reportTemplateAsStream;
        this.params = params;
    }

    @Override
    public void execute(Connection connection) throws SQLException {
        try {
            addDefaultParams();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplateAsStream);
            jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);
        } catch (JRException e) {
            logger.log(Level.SEVERE, "Não foi possível preencher o relatório", e);
        }
    }

    private void addDefaultParams() {
        try {
            // Sempre adiciona o logo do SIE aos params
            BufferedImage image = ImageIO.read(getClass().getResource("/reports/SIE.png"));
            params.put("LOGO", image);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Não foi possível adicionar o logo do SIE ao relatório.", e);
        }
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }
}
