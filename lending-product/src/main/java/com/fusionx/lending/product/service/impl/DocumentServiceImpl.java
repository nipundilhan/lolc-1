package com.fusionx.lending.product.service.impl;

import com.fusionx.lending.product.base.MessagePropertyBase;
import com.fusionx.lending.product.config.CommonModuleProperties;
import com.fusionx.lending.product.core.LogginAuthentcation;
import com.fusionx.lending.product.domain.Document;
import com.fusionx.lending.product.enums.CommonStatus;
import com.fusionx.lending.product.enums.YesNo;
import com.fusionx.lending.product.exception.DuplicateRecordException;
import com.fusionx.lending.product.exception.ValidateRecordException;
import com.fusionx.lending.product.repository.DocumentRepository;
import com.fusionx.lending.product.resources.DocumentAddResource;
import com.fusionx.lending.product.resources.DocumentUpdateResource;
import com.fusionx.lending.product.service.DocumentService;
import com.fusionx.lending.product.service.LendingAccountDetailService;
import com.fusionx.lending.product.service.RemoteService;
import com.fusionx.lending.product.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * API Service related to  document
 *
 * @author Rohan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author       Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        26-10-2021      -               -           Rohan      Created
 * <p>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class DocumentServiceImpl extends MessagePropertyBase implements DocumentService {

    private DocumentRepository documentRepository;
    private LendingAccountDetailService lendingAccountDetailService;
    private ValidationService validationService;
    private RemoteService remoteService;
    private CommonModuleProperties commonModuleProperties;

    @Autowired
    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Autowired
    public void setLendingAccountDetailService(LendingAccountDetailService lendingAccountDetailService) {
        this.lendingAccountDetailService = lendingAccountDetailService;
    }

    @Autowired
    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Autowired
    public void setRemoteService(RemoteService remoteService) {
        this.remoteService = remoteService;
    }

    @Autowired
    public void setCommonModuleProperties(CommonModuleProperties commonModuleProperties) {
        this.commonModuleProperties = commonModuleProperties;
    }

    @Override
    public Document addDocument(String tenantId, DocumentAddResource documentAddResource) {
        Optional<Document> isPresentDocument = documentRepository.findByLendingAccountDetail(
                lendingAccountDetailService.getByLendingAccountDetailById(Long.valueOf(documentAddResource.getLendingAccountDetailId())).get());

        if (isPresentDocument.isPresent())
            throw new DuplicateRecordException(environment.getProperty(COMMON_DUPLICATE), "lendingAccountDetailId");

        if (!remoteService.checkIsExist(tenantId, documentAddResource.getDocumentId(), commonModuleProperties.getComnCommon().concat("/system-generate-document-type/")))
            throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "document id");


        Document document = new Document();
        document.setTenantId(tenantId);
        document.setIsPrinted(YesNo.valueOf(documentAddResource.getIsPrinted()));
        document.setStatus(CommonStatus.valueOf(documentAddResource.getStatus()));
        document.setCreatedDate(validationService.getCreateOrModifyDate());
        document.setCreatedUser(LogginAuthentcation.getInstance().getUserName());
        document.setSyncTs(validationService.getCreateOrModifyDate());
        document.setLendingAccountDetail(lendingAccountDetailService.getByLendingAccountDetailById(Long.valueOf(documentAddResource.getLendingAccountDetailId())).get());
        document.setDocumentId(Long.valueOf(documentAddResource.getDocumentId()));

        return documentRepository.save(document);
    }

    @Override
    public Optional<Document> getById(Long id) {
        return documentRepository.findById(id);
    }

    @Override
    public Document updateDocument(String tenantId, Long id, DocumentUpdateResource documentUpdateResource) {
        Optional<Document> isPresentDocument = documentRepository.findById(id);

        if (isPresentDocument.isPresent()) {

            if (isPresentDocument.get().getId() != documentRepository.findByLendingAccountDetail(
                    lendingAccountDetailService.getByLendingAccountDetailById(Long.valueOf(documentUpdateResource.getLendingAccountDetailId())).get()).get().getId())
                throw new DuplicateRecordException(environment.getProperty(COMMON_DUPLICATE), "lendingAccountDetailId");

            if (!remoteService.checkIsExist(tenantId, documentUpdateResource.getDocumentId(), commonModuleProperties.getComnCommon().concat("/system-generate-document-type/")))
                throw new ValidateRecordException(environment.getProperty("common-invalid.id"), "document id");

            if (!isPresentDocument.get().getVersion().equals(Long.parseLong(documentUpdateResource.getVersion())))
                throw new ValidateRecordException(environment.getProperty("common.invalid-value"), "version");

            Document document = isPresentDocument.get();
            document.setTenantId(tenantId);
            document.setIsPrinted(YesNo.valueOf(documentUpdateResource.getIsPrinted()));
            document.setStatus(CommonStatus.valueOf(documentUpdateResource.getStatus()));
            document.setModifiedDate(validationService.getCreateOrModifyDate());
            document.setModifiedUser(LogginAuthentcation.getInstance().getUserName());
            document.setSyncTs(validationService.getCreateOrModifyDate());
            document.setLendingAccountDetail(lendingAccountDetailService.getByLendingAccountDetailById(Long.valueOf(documentUpdateResource.getLendingAccountDetailId())).get());
            document.setDocumentId(Long.valueOf(documentUpdateResource.getDocumentId()));

            return documentRepository.saveAndFlush(document);

        } else
            throw new ValidateRecordException(environment.getProperty("record-not-found"), "message");

    }

    @Override
    public List<Document> getByStatus(String status) {
        return documentRepository.findByStatus(CommonStatus.valueOf(status));
    }

    @Override
    public Optional<Document> getByLendingAccount(Long lendingAccountId) {
        return documentRepository.findByLendingAccountDetail(lendingAccountDetailService.getByLendingAccountDetailById(lendingAccountId).get());
    }

    @Override
    public List<Document> getAll() {
        return documentRepository.findAll();
    }
}
