package ca.uqac.keepitcool.menu.revealview;

import android.content.Context;
import android.content.Intent;

import ca.uqac.keepitcool.menu.MainActivity;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Main implements IXposedHookLoadPackage, IXposedHookZygoteInit {

    public static final String PACKAGE_NAME = Main.class.getPackage().getName();
    public static final String CLASS_GLOBAL_ACTIONS = "com.android.internal.policy.impl.GlobalActions";
    public static final String CLASS_GLOBAL_POWER_ACTIONS = "com.android.internal.policy.impl.GlobalActions.PowerAction";

    Context mContext;
    Context context;

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {}

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("ca.uqac.myfirstgame")) XposedBridge.log("in myapp");
        final Class<?> globalActionsClass = XposedHelpers.findClass(CLASS_GLOBAL_ACTIONS, lpparam.classLoader);
        XposedBridge.hookAllConstructors(globalActionsClass, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(final MethodHookParam param) throws Throwable {
                mContext = (Context) param.args[0];
            }
        });
        XposedHelpers.findAndHookMethod(CLASS_GLOBAL_POWER_ACTIONS, lpparam.classLoader, "onPress", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                showDialog();
                return null;
            }

        });
    }

    private void showDialog() {
        if (mContext == null) {
            return;
        }

        try {
            context = mContext.createPackageContext(PACKAGE_NAME, Context.CONTEXT_IGNORE_SECURITY);
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(intent);
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }
}