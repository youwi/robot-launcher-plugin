package com.github.nuclearg;

import com.intellij.codeInsight.generation.ClassMember;
import com.intellij.codeInsight.generation.GenerateGetterAndSetterHandler;
import com.intellij.codeInsight.generation.GenerationInfo;
import com.intellij.codeInsight.generation.PsiFieldMember;
import com.intellij.codeInsight.generation.actions.BaseGenerateAction;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocToken;
import com.intellij.util.IncorrectOperationException;

import java.util.ArrayList;
import java.util.List;

public class SmartGSAction extends BaseGenerateAction {
    int afb;
    public SmartGSAction() {
        super(new GenerateGetterAndSetterHandler() {
            public GenerationInfo[] generateMemberPrototypes(PsiClass aClass, ClassMember original) throws IncorrectOperationException {
                try {
                    List<GenerationInfo> methods = new ArrayList<>();
                    if (original instanceof PsiFieldMember) {
                        PsiFieldMember fieldMember = (PsiFieldMember) original;
                        PsiField field = fieldMember.getElement();

                        if (field.getModifierList() != null && field.getModifierList().hasExplicitModifier("static"))
                            return GenerationInfo.EMPTY_ARRAY;

                        methods.add(buildGetter(fieldMember));

                        if (field.getModifierList() != null && !field.getModifierList().hasExplicitModifier("final"))
                            methods.add(buildSetter(fieldMember));
                    }
                    return methods.toArray(GenerationInfo.EMPTY_ARRAY);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return GenerationInfo.EMPTY_ARRAY;
                }
            }


            private GenerationInfo buildGetter(PsiFieldMember fieldMember) {
                GenerationInfo generationInfo = fieldMember.generateGetter();
                insertJavaDoc(fieldMember.getElement(), generationInfo);
                return generationInfo;
            }


            private GenerationInfo buildSetter(PsiFieldMember fieldMember) {
                GenerationInfo generationInfo = fieldMember.generateSetter();
                insertJavaDoc(fieldMember.getElement(), generationInfo);
                return generationInfo;
            }

            private void insertJavaDoc(PsiField field, GenerationInfo generationInfo) {
                if (generationInfo == null)
                    return;

                String javadoc;
                if (field.getDocComment() == null)
                    javadoc = "/** " + field.getName() + " */";
                else {
                    StringBuilder builder = new StringBuilder();
                    builder.append("/**");
                    PsiElement e = field.getDocComment().getFirstChild();
                    while ((e = e.getNextSibling()) != null) {
                        if (!(e instanceof PsiDocToken)) continue;
                        PsiDocToken token = (PsiDocToken) e;
                        if (token.getTokenType() == JavaDocTokenType.DOC_COMMENT_DATA)
                            builder.append(token.getText());
                    }
                    builder.append(" */");
                    javadoc = builder.toString();
                }

                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(field.getProject());
                PsiDocComment javadocElement = elementFactory.createDocCommentFromText(javadoc);

                generationInfo.getPsiMember().addBefore(javadocElement, generationInfo.getPsiMember().getFirstChild());
            }

        });
    }

}